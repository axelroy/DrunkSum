try:
    import Image
except ImportError:
    from PIL import Image
import pytesseract


print(pytesseract.image_to_string(Image.open('samples/01_cropped.jpg')))
print(pytesseract.image_to_string(Image.open('samples/02_cropped.jpg')))
# print(pytesseract.image_to_string(Image.open('test-european.jpg'), lang='fra'))
