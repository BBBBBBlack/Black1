import torchvision
from torch.utils import tensorboard
import func

myFunc = func.FUNC()
toTensor = myFunc.getToTensor()
mnist_train = torchvision.datasets.MNIST(root="./dataset", train=True, transform=toTensor, download=True)
mnist_test = torchvision.datasets.MNIST(root="./dataset", train=False, transform=toTensor, download=True)
writer = tensorboard.SummaryWriter("logs")
for i in range(10):
    # mnist_train返回PILImage label
    writer.add_image("train", mnist_train[i][0], global_step=i)
for i in range(10):
    writer.add_image("test", mnist_test[i][0], global_step=i)
writer.close()
