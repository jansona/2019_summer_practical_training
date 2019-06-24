import sys
import os
from keras_face.library.face_net import FaceNet


def main():

    if len(sys.argv) < 3:

        print("usage: face_recognition_launcher <path> <id>")
        return 1

    image_path = sys.argv[1]
    person_id = sys.argv[2]

    model_dir_path = './demo/models'
    image_dir_path = "./demo/data/images/"

    fnet = FaceNet()
    fnet.load_model(model_dir_path)

    database = {}

    for root, dirs, files in os.walk(image_dir_path):
        for file in files:
            # print(image_dir_path + file)
            database[file.split('.')[0]] = fnet.img_to_encoding(image_dir_path + file)

    dist, is_valid = fnet.verify(image_path, person_id, database)

    is_found = False
    identity = None

    if is_valid:
        is_found = True
    else:
        dist, identity = fnet.who_is_it(image_path, database)

    print(str(is_found) + " : " + identity)
    return is_found, identity
        

if __name__ == '__main__':
    main()


    
