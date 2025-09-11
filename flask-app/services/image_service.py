from pymongo import MongoClient
from models.image_model import ImageDocument
from bson import ObjectId

class ImageService:
    def __init__(self, mongo_uri):
        self.client = MongoClient(mongo_uri)
        self.db = self.client.image_db
        self.collection = self.db.images

    def upload_image(self, file_storage):
        # file_storage est un Werkzeug FileStorage (Flask upload)
        image = ImageDocument(
            name=file_storage.filename,
            content_type=file_storage.content_type,
            image_data=file_storage.read()
        )
        result = self.collection.insert_one(image.to_dict())
        image.id = str(result.inserted_id)
        return image.id

    def get_image(self, image_id):
        data = self.collection.find_one({"_id": ObjectId(image_id)})
        return data
