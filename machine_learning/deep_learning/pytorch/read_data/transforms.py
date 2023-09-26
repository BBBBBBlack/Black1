from torchvision import transforms
import dataset
from torch.utils import tensorboard

writer = tensorboard.SummaryWriter("logs")
dset = dataset.MyDataSet("D:\\course\\pythonnn\\hymenoptera_data\\train", "ants")
img = dset[15]

# ToTensor
tensor = transforms.ToTensor()
img_tensor = tensor(img[0])
writer.add_image("tensor", img_tensor)

# Compose, Normalize
compose = transforms.Compose(
    [
        transforms.Resize(256),
        tensor,
        transforms.Normalize([0.5, 0.5, 0.5], [0.5, 0.5, 0.5])
    ]
)
img_compose = compose(img[0])
writer.add_image("compose", img_compose)
writer.close()
