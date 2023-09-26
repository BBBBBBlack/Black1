import numpy
import pandas
import matplotlib.pyplot as pyplot

# 1. 画图
df = pandas.read_csv('D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex1-linear regression\\ex1data1.txt', header=None)
x = numpy.asarray(df[0])
y = numpy.asarray(df[1])
fig, ax = pyplot.subplots()
ax.scatter(x, y, marker='x', c='r', s=20)

# 2.单变量线性回归
ver_x = numpy.insert(numpy.matrix(x).transpose(), 0, values=1, axis=1)
ver_theta = numpy.matrix(numpy.zeros([2, 1]))
alpha = 0.01
temp = (ver_x * ver_theta) - numpy.matrix(y).transpose()
for i in range(1000):
    ver_theta = ver_theta - ver_x.transpose() * temp * alpha / x.size
    temp = (ver_x * ver_theta) - numpy.matrix(y).transpose()
else:
    cost_func = (temp.T * temp).astype(float) / (2 * x.size)
    print('cost_func:', cost_func, end='\n')
    print('theta:', ver_theta)

# 3.画拟合图
x_ = numpy.arange(5, 23, 0.05)
x_ = numpy.matrix(x_)
x_ = numpy.insert(x_.transpose(), 0, values=1, axis=1)
y_ = (x_ * ver_theta).transpose()
ax.plot(x_[:, 1].T, y_, 'bo', ms=1)
pyplot.show()
