import base64
from flask import Flask
from flask import request
import cv2
import skimage.filters

app = Flask(__name__)


@app.route('/authenticate',methods = ['POST', 'GET'])
def authenticate():
    if request.method == ['POST']:
        #encodedImg, option, 
        val = request.data
        print(val)
    #imgdata = encodedImg.decode('UTF-8')

    """ filename = 'image.jpg'
    with open(filename, 'wb') as f:
        f.write(imgdata)

    if option == 0:    
        image = cv2.imread('image.jpg')
        blurred_img = cv2.GaussianBlur((0,0), (val,val) ,.3)
        return base64.b64encode(blurred_img)

    elif option == 1:    
        image = cv2.imread('image.jpg')
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        return base64.b64encode(gray)
    
    elif option == 2:
        image = cv2.imread('image.jpg')
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        img = skimage.filters.Gaussian(gray, sigma=1.0)
        t = val
        binary_mask = img < t
        return base64.b64encode(binary_mask) """


if __name__ == '__main__':
        app.run(host='127.0.0.1')