import matplotlib.pyplot as pyplot
import numpy
import scipy

import func

# 1.画图
data = scipy.io.loadmat("D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex3-neural network\\ex3data1.mat")
fea = data['X']  # (5000,400)
y = data['y']  # (5000,1)
pyplot.imshow(numpy.asarray(fea).reshape((5000, 20, 20))[4000].T, cmap='gray')
pyplot.show()
# 2.代价函数
fea = numpy.insert(numpy.matrix(fea), 0, values=1, axis=1)  # (5000,400)
theta = numpy.matrix(numpy.zeros((fea.shape[1], 1)))  # (400,1)
y = numpy.matrix(y).T
res_func = func.classifier(y, fea, theta, 0.01)
print(func.getStore(y, fea, res_func))
