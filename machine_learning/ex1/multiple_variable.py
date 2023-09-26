import numpy
import pandas
import matplotlib.pyplot as pyplot

# 1.梯度下降
i_df = pandas.read_csv('D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex1-linear regression\\ex1data2.txt', header=None)
df = (i_df - i_df.mean()) / i_df.std()
res = numpy.matrix(numpy.asarray(df[i_df.columns.size - 1])).transpose()
fea = numpy.matrix(numpy.insert(numpy
                                .asarray(df.iloc[:, 0:i_df.columns.size - 1]), 0, values=1, axis=1))
theta = numpy.matrix(numpy.zeros([i_df.columns.size, 1]))
alpha = 0.01
for i in range(1000):
    theta = theta - fea.transpose() * (fea * theta - res) * alpha / i_df.index.size
cost_func = (fea * theta - res).T * (fea * theta - res) / (2 * i_df.index.size)
print('cost_func:', cost_func, end='\n')
print('theta:', theta)

# 2.正规方程
theta_normal = numpy.linalg.inv(fea.transpose() * fea) * fea.transpose() * res
print(theta_normal)
