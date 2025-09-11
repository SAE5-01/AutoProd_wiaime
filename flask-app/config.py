class Config:
    MONGO_URI = "mongodb://localhost:27017/image_db"

    # Configuration pour hMailServer local
    MAIL_SERVER = 'localhost'             # hMailServer local
    MAIL_PORT = 25                        # ou 587 si configuré
    MAIL_USE_TLS = False
    MAIL_USE_SSL = False
    MAIL_USERNAME = 'ed@monmail.com'    # Ton adresse créée
    MAIL_PASSWORD = 'changeme'          # Mot de passe associé
    MAIL_DEFAULT_SENDER = 'ed@monmail.com'
