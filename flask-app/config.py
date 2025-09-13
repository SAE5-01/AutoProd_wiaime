import os

class Config:
    # Utilise la variable d'env MONGO_URI si dispo, sinon fallback sur le service docker 'mongo-db'
    MONGO_URI = os.getenv("MONGO_URI", "mongodb://mongo-db:27017/image_db")

    # Configuration email (adaptable selon ton environnement)
    MAIL_SERVER = os.getenv("MAIL_SERVER", "localhost")
    MAIL_PORT = int(os.getenv("MAIL_PORT", 25))
    MAIL_USE_TLS = os.getenv("MAIL_USE_TLS", "false").lower() == "true"
    MAIL_USE_SSL = os.getenv("MAIL_USE_SSL", "false").lower() == "true"
    MAIL_USERNAME = os.getenv("MAIL_USERNAME", "ed@monmail.com")
    MAIL_PASSWORD = os.getenv("MAIL_PASSWORD", "changeme")
    MAIL_DEFAULT_SENDER = os.getenv("MAIL_DEFAULT_SENDER", "ed@monmail.com")
