from bson import ObjectId

class ImageDocument:
    def __init__(self, name, content_type, image_data):
        self.name = name
        self.content_type = content_type
        self.image_data = image_data
        self.id = None

    def to_dict(self):
        return {
            "name": self.name,
            "content_type": self.content_type,
            "image_data": self.image_data
        }
