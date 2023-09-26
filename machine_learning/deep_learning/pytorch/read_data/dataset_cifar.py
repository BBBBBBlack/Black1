import torchvision
from torch.utils import tensorboard
import func

myFunc = func.FUNC()
toTensor = myFunc.getToTensor()
cifar_train = torchvision.datasets.CIFAR10(root="./dataset", transform=toTensor, train=True, download=True)
cifar_test = torchvision.datasets.CIFAR10(root="./dataset", transform=toTensor, train=False, download=True)
writer = tensorboard.SummaryWriter("logs")
for i in range(10):
    # cifar_train返回PILImage label
    writer.add_image("train", cifar_train[i][0], global_step=i)
for i in range(10):
    writer.add_image("test", cifar_test[i][0], global_step=i)
writer.close()
