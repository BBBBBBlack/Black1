import numpy
import scipy.special


def changeY(y):
    new_y = None
    iter_y = numpy.nditer(y)
    for i in iter_y:
        temp = numpy.zeros((10, 1))
        temp[i - 1][0] = 1
        if new_y is None:
            new_y = numpy.asarray(temp)
        else:
            new_y = numpy.hstack((new_y, temp))
    return new_y


def sigmoid(x):
    return scipy.special.expit(x)


def gradient_sigmoid(x):
    gz = scipy.special.expit(x)
    return numpy.multiply(gz * (1 - gz))


# 假设函数
def hypothesis(x, theta1, theta2):
    # input layer
    x = numpy.insert(x, 0, values=1, axis=1).T  # (401,5000)
    # hidden layer
    z = numpy.matmul(theta1, x)  # (25,5000)
    a = sigmoid(z)
    # output layer
    a = numpy.insert(a, 0, values=1, axis=0)  # (26,5000)
    z2 = numpy.matmul(theta2, a)
    hx = sigmoid(z2)
    return x.T, a, hx.T  # (5000,401),(26,5000),(5000,10)


def cost_func(y, x, theta1, theta2, is_regularized, lambda_=None):
    x, a, hx = hypothesis(x, theta1, theta2)
    hx = numpy.clip(hx, 1e-15, 1 - 1e-15)
    temp = numpy.matmul(y, numpy.log(hx)) + numpy.matmul((1 - y), numpy.log(1 - hx))  # (10,10)
    cost = numpy.trace(temp) / (y.shape[1] * -1.0)
    if is_regularized:
        return cost + (numpy.power(theta1[:, 1:], 2).sum() + numpy.power(theta2[:, 1:], 2).sum()) \
            * lambda_ / (2 * y.shape[1])
    else:
        return cost


def gradient(y, fea, theta1, theta2, lambda_):
    x, a, hx = hypothesis(fea, theta1, theta2)
    delta3 = hx.T - y  # (10,5000)
    delta2 = numpy.multiply(numpy.matmul(theta2.T, delta3), numpy.multiply(a, (1 - a)))[1:]  # (25,5000)
    temp2 = theta2 * lambda_ / y.shape[1]
    temp2[:, 0] = 0
    temp1 = theta1 * lambda_ / y.shape[1]
    temp1[:, 0] = 0
    Delta2 = numpy.matmul(delta3, a.T) / y.shape[1] + temp2  # (10,26)
    Delta1 = numpy.matmul(delta2, x) / y.shape[1] + temp1  # (25,401)
    return numpy.concatenate((numpy.ravel(Delta1), numpy.ravel(Delta2)))


def backprop(theta, y, fea):
    theta1 = theta[:25 * 401].reshape((25, 401))
    theta2 = theta[25 * 401:].reshape((10, 26))
    J = cost_func(y, fea, theta1, theta2, True, 1)
    return J, gradient(y, fea, theta1, theta2, 1)


def getStore(theta, y, fea):
    theta1 = theta[:25 * 401].reshape((25, 401))
    theta2 = theta[25 * 401:].reshape((10, 26))
    res = hypothesis(fea, theta1, theta2)
    hx = res[2]  # (5000,10)
    max_indices = numpy.argmax(hx, axis=1)
    hx[:] = 0
    hx[numpy.arange(hx.shape[0]), max_indices] = 1
    temp = y.T - hx
    return 1 - temp[temp != 0].size / temp.shape[0]
