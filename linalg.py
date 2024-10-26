import numpy as np
import matplotlib.pyplot as plt
from scipy.linalg import *
from numpy.polynomial.polynomial import *


P = np.array([[0.2, 0.1, 0.3, 0.1],  #harry input for dairy
              [0.1, 0.3, 0.3, 0.3],  #jude input for eggs
              [0.1, 0.2, 0.1, 0.1],  #olivia input for veggies/grains
              [0.4, 0.5, 0.4, 0.4]]) #berlinda input for bread/quiche

productionGoals = np.array([0, 4, 3, 0])  
neededGoods = P @ productionGoals  

print("Goods needed for production: ", neededGoods)


d = np.array([136, 120, 65, 150])  # demand vector
I = np.eye(4)  # identity matrix
invMatrix = inv(I - P)  # calculate inverse to solve for total production needed
x = invMatrix @ d
print("Total production needed (x):", x)

#Graphical Predictions

#months since dec2022
monthsSinceDec2022 = np.arange(1, 14)
#historical table data
milkProduction = np.array([2010, 2052, 1880, 2099, 2039, 2110, 2016, 2027, 2011, 1943, 1993, 1929, 2014])

#plotting
plt.scatter(monthsSinceDec2022, milkProduction, color='blue')
plt.title('Milk Production Over Time')
plt.xlabel('Months since December 2022')
plt.ylabel('Average Milk Production / Cow')
plt.grid(True)
plt.show()

#fitting a model
degree = 3 #test degrees
coefs = Polynomial.fit(monthsSinceDec2022, milkProduction, degree).convert().coef

#future predictions
predict_march = Polynomial(coefs)(15)  #Mar2024 prediction
predict_june = Polynomial(coefs)(18)  #Jun2024 prediction

print(f"Predicted milk production for March 2024: {predict_march:.2f} pounds")
print(f"Predicted milk production for June 2024: {predict_june:.2f} pounds")


