import PIL.Image
import numpy
import pandas
import matplotlib.pyplot as pyplot
import scipy
import ex2.func


def changeY(y, num):
    temp = y.copy()
    temp[temp != num] = 0
    temp[temp == num] = 1
    return temp


def getScore(num, theta, x, y):
    hx = ex2.func.hypothesis(x, theta)
    hx[hx < 0.5] = 0
    hx[hx >= 0.5] = 1
    temp = hx - y.T
    return temp[temp == 0].size / y.size


def getStore(y, x, theta):
    hx = ex2.func.hypothesis(x, theta)
    res = (numpy.argmax(hx, axis=1) + 1).T - y
    return res[res == 0].size / y.size


def classifier(y, x, theta, alpha):
    res_func = None
    for i in range(1, 11):
        temp = changeY(y, i)

        # 1.梯度下降手动版
        # res = ex2.func.gradient_descent(temp, x, theta, alpha, True, 1)
        # 2. 调用函数
        res, nfeval, rc = scipy.optimize.fmin_tnc(func=ex2.func.fmin_cost, x0=theta, fprime=ex2.func.fmin_fprime,
                                                  args=(x, temp))
        res = numpy.matrix(res).T

        if res_func is None:
            res_func = res
        else:
            res_func = numpy.hstack((res_func, res))
    return res_func
