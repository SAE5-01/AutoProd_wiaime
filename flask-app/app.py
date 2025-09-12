from flask import Flask, request, jsonify, send_file
from config import Config
from services.image_service import ImageService
from services.email_service import EmailService
from io import BytesIO

app = Flask(__name__)
app.config.from_object(Config)

# Initialiser les services
image_service = ImageService(app.config['MONGO_URI'])
email_service = EmailService(app)

# Route par défaut pour éviter le 404 sur la racine (/)
@app.route('/')
def home():
    return "Welcome to the Image Upload Service API"

@app.route('/api/images/upload', methods=['POST'])
def upload_image():
    if 'file' not in request.files:
        return jsonify({"error": "No file part"}), 400
    
    file = request.files['file']
    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400
    
    try:
        # Upload de l'image et récupération de l'ID
        image_id = image_service.upload_image(file)
        
        # Envoi d'email de confirmation
        email_service.send_email(
            to_email="user@example.com",  
            subject="Image uploaded",
            body=f"Votre image a été uploadée avec l'ID: {image_id}"
        )
        
        return jsonify({"message": "Image uploaded successfully", "image_id": image_id}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/images/<image_id>', methods=['GET'])
def get_image(image_id):
    image_data = image_service.get_image(image_id)
    if not image_data:
        return jsonify({"error": "Image not found"}), 404
    
    return send_file(
        BytesIO(image_data['image_data']),
        mimetype=image_data['content_type'],
        download_name=image_data['name']
    )

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
