import copy

class Prototype:
    def clone(self):
        return copy.deepcopy(self)

class Document(Prototype):
    def __init__(self, text, images):
        self.text = text
        self.images = images

    def __str__(self):
        return f"Text: {self.text}, Images: {self.images}"

doc1 = Document("Hello", ["img1.png"])
doc2 = doc1.clone()
doc2.text = "Hello, world!"

print(doc1)
print(doc2)