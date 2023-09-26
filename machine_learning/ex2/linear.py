import numpy
import pandas
import matplotlib.pyplot as pyplot
import scipy.optimize
import func

# 1. 画图
df = pandas.read_csv('D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex2-logistic regression\\ex2data1.txt', header=None)
x0 = []
y0 = []
x1 = []
y1 = []
for row in df.itertuples():
    # 没被录取
    if row[3] == 0:
        x0.append(row[1])
        y0.append(row[2])
    else:
        x1.append(row[1])
        y1.append(row[2])
numpy.asarray(x0)
numpy.asarray(y0)
numpy.asarray(x1)
numpy.asarray(y1)

fig, ax = pyplot.subplots()
ax.scatter(x0, y0, s=10, c="green", marker="x")
ax.scatter(x1, y1, s=10, c="red", marker="o")
# 2. 代价函数
x = numpy.matrix(df.loc[:, 0:1])
x = numpy.insert(x, 0, values=1, axis=1)  # (100,3)
theta = numpy.zeros((x.shape[1], 1))  # (3,1)
theta = numpy.matrix(theta)
y = numpy.matrix(df[2])  # (1,100)
# 3. 梯度下降
#   3.1 自主实现
res = func.gradient_descent(y, x, theta, 0.001, False)
xx = []
yy = []
for i in range(30, 100):
    xx.append(i)
    yy.append((res[0, 0] + res[1, 0] * i) / (res[2, 0] * -1.0))
ax.plot(numpy.asarray(xx), numpy.asarray(yy))
#   3.2 scipy函数
# 为啥theta一定会转换为shape=(1,3)？神经病
res2, nfeval, rc = scipy.optimize.fmin_tnc(func=func.fmin_cost, x0=theta, fprime=func.fmin_fprime, args=(x, y))
xx = []
yy = []
for i in range(30, 100):
    xx.append(i)
    yy.append((res2[0] + res2[1] * i) / (res2[2] * -1.0))
ax.plot(numpy.asarray(xx), numpy.asarray(yy), c="red")
pyplot.show()
