import torchvision


class FUNC:
    toTensor = None

    def getToTensor(self):
        if self.toTensor is not None:
            return self.toTensor
        self.toTensor = torchvision.transforms.Compose([
            torchvision.transforms.ToTensor()
        ])
        return self.toTensor
