import numpy
import scipy.special


def sigmoid(x):
    return scipy.special.expit(x)


# 假设函数
def hypothesis(x, theta):
    return sigmoid(x * theta)


def cost_func(theta, x, y):
    hx = hypothesis(x, theta)
    return (y * numpy.log(hx) + (1 - y) * numpy.log(1 - hx)) / (y.size * -1.0)


def gradient(y, x, theta, alpha):
    return ((hypothesis(x, theta).T - y) * x).T * alpha / (y.size * 1.0)


def gradient_descent(y, x, theta, alpha, is_regularized, lambda_=None):
    if not is_regularized:
        for i in range(3000):
            # print(hypothesis(gx, gtheta).T)
            theta = theta - gradient(y, x, theta, alpha)
    else:
        for i in range(3000):
            if i == 0:
                theta = theta - gradient(y, x, theta, alpha)
            else:
                theta = (1 - alpha * lambda_ / y.size) * theta - gradient(y, x, theta, alpha)
    return theta


def create_feature(max_power, x1, x2):
    features = None
    for i in range(max_power + 1):
        for j in range(i + 1):
            if features is None:
                features = numpy.asarray(numpy.power(x1, j) * numpy.power(x2, i - j))
            else:
                # print("x1^" + str(j), "x2^" + str(i - j))
                new_col = numpy.asarray(numpy.power(x1, j) * numpy.power(x2, i - j))
                features = numpy.vstack((features, new_col))
    return features


def equation(theta, max_power, X, Y):
    Z = None
    for i in range(X.shape[1]):
        new_col = create_feature(max_power, numpy.asarray(X[i]).ravel(),
                                 numpy.asarray(Y[i]).ravel()).T * theta
        if Z is None:
            Z = numpy.asarray(new_col.T)
        else:
            # print("x1^" + str(j), "x2^" + str(i - j))
            Z = numpy.vstack((Z, new_col.T))
    return Z


def fmin_fprime(theta, x, y):
    theta = numpy.matrix(theta).T
    hx = hypothesis(x, theta)
    return ((hx.T - y) * x) / (y.size * 1.0)


def fmin_cost(theta, x, y):
    theta = numpy.matrix(theta).T
    hx = hypothesis(x, theta)
    return (y * numpy.log(hx) + (1 - y) * numpy.log(1 - hx)) / (y.size * -1.0)
