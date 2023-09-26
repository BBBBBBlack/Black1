import numpy
import pandas
import matplotlib.pyplot as pyplot
import func


def cost_func(theta, x, y, lambda_):
    return func.cost_func(theta, x, y) + lambda_ / y.size * numpy.sum(numpy.power(theta, 2))


# 1. 画图
df = pandas.read_csv('D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex2-logistic regression\\ex2data2.txt', header=None)
negative = df[df[2].isin([0])]
positive = df[df[2].isin([1])]
fig, ax = pyplot.subplots()
ax.scatter(negative[0], negative[1], s=10, c="green", marker="x")
ax.scatter(positive[0], positive[1], s=10, c="red", marker="o")
# 数据处理（造变量）
x1 = numpy.asarray(df[0])
x2 = numpy.asarray(df[1])
features = func.create_feature(6, x1, x2)
# 2. 代价函数
features = numpy.matrix(features).T
y = numpy.matrix(df[2])
theta = numpy.matrix(numpy.zeros((features.shape[1], 1)))
print(cost_func(theta, features, y, 1))
# 3. 梯度下降
#   3.1 自主实现
res = func.gradient_descent(y, features, 0.01, False)
#       3.1.1 三维图
xx1 = numpy.linspace(-1, 1, 100)
xx2 = numpy.linspace(-1, 1, 100)
#           3.1.1.1 创建网格数据
X, Y = numpy.meshgrid(xx1, xx2)
X = numpy.matrix(X)
Y = numpy.matrix(Y)
#           3.1.1.2 计算方程的值
Z = func.equation(res, 6, X, Y)
# #           3.1.1.3创建3D图形对象
# fig2 = pyplot.figure()
# ax = fig2.add_subplot(111, projection='3d')
# #           3.1.1.4绘制
# ax.plot_surface(X, Y, Z, cmap='viridis')
#       3.1.2 三维图截面
# 绘制截面图
pyplot.contour(X, Y, Z, levels=[0], cmap='jet')
pyplot.show()
