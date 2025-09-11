from flask_mail import Mail, Message

class EmailService:
    def __init__(self, app):
        self.mail = Mail(app)

    def send_email(self, to_email, subject, body):
        msg = Message(subject=subject,
                      recipients=[to_email],
                      body=body)
        self.mail.send(msg)
