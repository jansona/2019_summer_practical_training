from keras_face.library.face_net import FaceNet

def main():
    model_dir_path = './demo/models'
    image_dir_path = "./demo/data/images"

    fnet = FaceNet()
    # fnet.load_model(model_dir_path)

    database = {}
#     database["danielle"] = fnet.img_to_encoding(image_dir_path + "/danielle.png")
    # fnet.img_to_encoding(image_dir_path + "/lyz.jpg")
    database["ybg"] = fnet.img_to_encoding(image_dir_path + "/ybg.jpg")
    database["tian"] = fnet.img_to_encoding(image_dir_path + "/tian.jpg")

    # verifies whether a particular camera image is a person in the image database
    dist, is_valid = fnet.verify(image_dir_path + "/camera_6.jpg", "ybg", database)
    print('camera_6.jpg is' + (' ' if is_valid else ' not ') + 'ybg')
    
    # whether a particular camera image is which person in the image database (or not at all)
    dist, identity = fnet.who_is_it(image_dir_path + "/camera_6.jpg", database)
    if identity is None:
        print('camera_6.jpg is not found in database')
    else:
        print('camera_6.jpg is ' + str(identity))


if __name__ == "__main__":
    main()