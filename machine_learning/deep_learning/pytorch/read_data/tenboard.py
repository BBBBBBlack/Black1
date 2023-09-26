import numpy
from torch.utils import tensorboard

import dataset

writer = tensorboard.SummaryWriter("logs")

# scalar
for i in range(100):
    writer.add_scalar("y=2x", 2 * i, i)

# image
dset = dataset.MyDataSet("D:\\course\\pythonnn\\hymenoptera_data\\train", "ants")
for i in range(1, 6):
    img = dset[i]
    img_array = numpy.asarray(img[0])
    writer.add_image("ants", img_array, i, dataformats="HWC")
dset = dataset.MyDataSet("D:\\course\\pythonnn\\hymenoptera_data\\train", "bees")
for i in range(1, 6):
    img = dset[i]
    img_array = numpy.asarray(img[0])
    writer.add_image("bees", img_array, i, dataformats="HWC")

writer.close()
