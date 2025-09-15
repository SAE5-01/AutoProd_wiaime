from flask import Flask, request, jsonify, send_file
from config import Config
from services.image_service import ImageService
from services.email_service import EmailService
from io import BytesIO

app = Flask(__name__)
app.config.from_object(Config)

# Services
image_service = ImageService(app.config["MONGO_URI"])
email_service = EmailService(app)

# --- Health & Home ------------------------------------------------------------

@app.route("/health", methods=["GET"])
def health():
    """Simple probe pour savoir si l'app est vivante."""
    return jsonify({"status": "OK"}), 200

@app.route("/", methods=["GET"])
def home():
    return "Welcome to the Image Upload Service API", 200

# --- API Images ---------------------------------------------------------------

@app.route("/api/images/upload", methods=["POST"])
def upload_image():
    # validations simples
    if "file" not in request.files:
        return jsonify({"error": "No file part"}), 400

    file = request.files["file"]
    if not file or file.filename.strip() == "":
        return jsonify({"error": "No selected file"}), 400

    try:
        # 1) Upload en base (GridFS/collection selon ton ImageService)
        image_id = image_service.upload_image(file)

        # 2) Essayer d'envoyer l'email, mais NE PAS faire échouer l'upload
        try:
            email_service.send_email(
                to_email="user@example.com",
                subject="Image uploaded",
                body=f"Votre image a été uploadée avec l'ID: {image_id}",
            )
        except Exception as mail_err:
            # On log seulement; la réponse reste 201 Created
            app.logger.warning(f"Email sending failed (non-blocking): {mail_err}")

        return jsonify(
            {"message": "Image uploaded successfully", "image_id": str(image_id)}
        ), 201

    except Exception as e:
        # Toute autre erreur côté upload renvoie 500
        app.logger.exception("Upload failed:")
        return jsonify({"error": str(e)}), 500


@app.route("/api/images/<image_id>", methods=["GET"])
def get_image(image_id):
    try:
        image_data = image_service.get_image(image_id)
        if not image_data:
            return jsonify({"error": "Image not found"}), 404

        return send_file(
            BytesIO(image_data["image_data"]),
            mimetype=image_data.get("content_type", "application/octet-stream"),
            download_name=image_data.get("name", f"{image_id}"),
        )
    except Exception as e:
        app.logger.exception("Get image failed:")
        return jsonify({"error": str(e)}), 500


# --- Entrypoint local (utile si tu lances python app.py) ----------------------

if __name__ == "__main__":
    # En prod on utilise gunicorn, mais ceci permet de tester localement
    app.run(host="0.0.0.0", port=5000, debug=False)
