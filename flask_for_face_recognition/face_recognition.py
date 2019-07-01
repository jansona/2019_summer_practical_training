import os
import subprocess

from flask import Flask
app = Flask(__name__)

@app.route('/')
def hello_world():
    child1 = subprocess.Popen(["face_recognition", "/home/jansona/photo/lost", "/home/jansona/temp"], stdout=subprocess.PIPE, shell=True)
    return child1.stdout.read()

if __name__ == '__main__':
    app.run()