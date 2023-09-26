import scipy
import numpy

import ex2.func

data = scipy.io.loadmat("D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex3-neural network\\ex3data1.mat")
thetas = scipy.io.loadmat("D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex3-neural network\\ex3weights.mat")
theta1 = thetas['Theta1']
theta2 = thetas['Theta2']
x = numpy.insert(numpy.matrix(data['X']), 0, values=1, axis=1)  # (5000,401)
y = numpy.matrix(data['y'])
a1 = ex2.func.sigmoid(theta1 * x.T)  # (25,5000)
a1 = numpy.insert(a1, 0, values=1, axis=0)  # (26,5000)
a2 = ex2.func.sigmoid(theta2 * a1)  # (10,5000)
res = numpy.argmax(a2, axis=0) + 1 - y.T
print(res[res == 0].size / y.size)
