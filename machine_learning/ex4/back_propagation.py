import numpy
import scipy.optimize
import scipy.io
import func

data = scipy.io.loadmat("D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex4-NN back propagation\\ex4data1.mat")
thetas = scipy.io.loadmat("D:\\xxx\\Coursera-ML-AndrewNg-Notes\\code\\ex4-NN back propagation\\ex4weights.mat")
fea = data['X']  # (5000,400)
y = data['y']  # (5000,1)
y = func.changeY(y)  # (10,5000)
theta1 = numpy.asarray(thetas['Theta1'])  # (25,401)
theta2 = numpy.asarray(thetas['Theta2'])  # (10,26)
# 1. 代价函数
cost = func.cost_func(y, fea, theta1, theta2, True, 1)
# 2. 初始化theta
epsilon = 0.12
theta1 = numpy.random.rand(25, 401) * 2 * epsilon - epsilon
theta2 = numpy.random.rand(10, 26) * 2 * epsilon - epsilon
theta = numpy.concatenate((numpy.ravel(theta1), numpy.ravel(theta2)))
res = scipy.optimize.minimize(fun=func.backprop, x0=theta, args=(y, fea), jac=True, method="TNC",
                              options={'maxiter': 250})
store = func.getStore(res['x'], y, fea)
