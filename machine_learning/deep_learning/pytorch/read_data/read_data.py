import dataset

dset = dataset.MyDataSet("D:\\course\\pythonnn\\hymenoptera_data\\train", "ants")
img, label = dset[90]
img.show()
