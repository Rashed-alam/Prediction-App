import numpy as np 
import matplotlib.pyplot as plt 
from matplotlib import style
from sklearn.cluster import KMeans

style.use('ggplot')

# load the data
x = []
y = []
for line in open('regress.csv'):
    X, Y = line.split(',')
    x.append(float(X))
    y.append(float(Y))

plt.scatter(x,y)


# converting x y to 2d X
X = np.column_stack((x,y))


# k - means algorthm
kmeans = KMeans(n_clusters=2)
kmeans.fit(X)

centeroids = kmeans.cluster_centers_
labels = kmeans.labels_

print(centeroids)
print(labels)

# color for clusters

color = ["g.", "r.", "c.","y."]

for i in range(len(X)):
	print("coordinate: ", X[i], "label:", labels[i])
	plt.plot(X[i][0], X[i][1], color[labels[i]], markersize = 10)

plt.scatter(centeroids[:, 0], centeroids[:, 1], marker= 'x', s=200, linewidths=5, zorder=10)
# showing graphs
plt.show()
