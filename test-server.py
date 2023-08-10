from flask import Flask, request
import io
import PIL.Image as Image
import cv2
import base64
import sys
from itsdangerous import base64_encode
import numpy as np
from skimage.io import imread

app = Flask(__name__)
img = ''
path = 'D:/CIT/College/Projects/Image Filter App/image.jpg'

def image_save(data):
    img = Image.open(io.BytesIO(data))
    img.save(path) 

def image_open():
    img = imread(path)    
    return img


@app.route('/')
def hello_world():
    return 'Hello World!'

@app.route('/upload/', methods = ['POST'])
def img_upload():
    if request.method == 'POST':
        imgdata = request.data
        image_save(imgdata)

    return "Image uploaded"

@app.route('/blur/<value>', methods = ['GET', 'POST'])
def image_blur(value):
    img = request.data
    image_save(img)
    img = image_open()

    blur_val = int(value)
    blurred_img = cv2.GaussianBlur(img,(blur_val, blur_val) ,0)
    blurred_img = cv2.cvtColor(blurred_img, cv2.COLOR_BGR2RGB)
    is_success, img_arr = cv2.imencode(".jpg", blurred_img)
    
    print("Image blurring success...",file=sys.stderr)
    return base64.b64encode(img_arr.tobytes())

@app.route('/color_change', methods = ['GET','POST'])
def image_clr_change():
    img = request.data
    image_save(img)
    
    img = image_open()

    cc_img = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
    is_success, img_arr = cv2.imencode(".jpg", cc_img)
    
    print("Image color change success...",file=sys.stderr)
    return base64.b64encode(img_arr.tobytes())

@app.route('/flip/<value>', methods = ['GET','POST'])
def flip_image(value):
    img = request.data
    image_save(img)
    img = image_open()

    img = cv2.flip(img, int(value))
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    is_success, img_arr = cv2.imencode(".jpg", img)
    
    print("Image flip success...",file=sys.stderr)
    return base64.b64encode(img_arr.tobytes())

@app.route('/rotate/<direction>', methods = ['GET', 'POST'])
def rotate_image(direction):
    img = request.data
    image_save(img)
    img = image_open()
    direction = int(direction)

    if direction == 0:
        img = cv2.rotate(img, cv2.ROTATE_90_COUNTERCLOCKWISE)
    else:
        img = cv2.rotate(img, cv2.ROTATE_90_CLOCKWISE)

    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    is_success, img_arr = cv2.imencode(".jpg", img)
    
    print("Image rotate success...",file=sys.stderr)
    return base64.b64encode(img_arr.tobytes())

@app.route('/resize/<height>/<width>', methods = ['GET', 'POST'])
def resize_image(height, width):
    img = request.data
    image_save(img)
    img = image_open()
    
    height = int(height)
    width = int(width)
    img = cv2.resize(img, (width, height))

    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    is_success, img_arr = cv2.imencode(".jpg", img)
    
    print("Image resize success...",file=sys.stderr)
    return base64.b64encode(img_arr.tobytes())

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)