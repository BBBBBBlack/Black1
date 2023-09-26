import torch.utils.data as data
import PIL.Image as Image
import os


class MyDataSet(data.Dataset):

    def __init__(self, root, pkg):
        self.path = os.path.join(root, pkg)
        self.dir_list = os.listdir(self.path)
        self.label = pkg

    def __getitem__(self, idx):
        img_path = os.path.join(self.path, self.dir_list[idx])
        img = Image.open(img_path)
        return img, self.label
