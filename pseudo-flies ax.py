import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression

# Data for Ax and Ax flies from the original problem
ax_wing = [2.87, 4.02, 3.18, 3.51, 3.74, 3.95, 3.28]
ax_abdomen = [1.78, 1.86, 1.96, 2.00, 2.10, 1.96, 1.87]
ax_antenna = [1.14, 1.29, 1.30, 1.26, 1.39, 1.28, 1.28]

# Step 1: Fit linear regression to original data
def plot_regression(wing_data, antenna_data, fly_type):
    X = np.array(wing_data).reshape(-1, 1)  # Wing length (independent)
    y = np.array(antenna_data)  # Antenna length (dependent)
    
    model = LinearRegression()
    model.fit(X, y)
    
    predicted = model.predict(X)

    # Plot original data and regression line
    plt.figure(figsize=(8, 6))
    plt.scatter(X, y, color='blue', label=f'{fly_type} Observed Data')
    plt.plot(X, predicted, color='red', label='Fitted Regression Line')
    plt.xlabel('Wing Length (cm)')
    plt.ylabel('Antenna Length (cm)')
    plt.title(f'Original Regression Model for {fly_type}')
    plt.legend()
    plt.show()
    
    return model, X, y

# Plot the original regression model for Ax flies
model_ax, X_ax, y_ax = plot_regression(ax_wing, ax_antenna, 'Ax')

# Step 2: Residual plot
def plot_residuals(X, y, model, fly_type):
    predicted = model.predict(X)
    residuals = y - predicted
    
    plt.figure(figsize=(8, 6))
    plt.scatter(X, residuals, color='green', label='Residuals')
    plt.axhline(0, color='red', linestyle='--')
    plt.xlabel('Wing Length (cm)')
    plt.ylabel('Residuals (Antenna Length)')
    plt.title(f'Residual Plot for {fly_type}')
    plt.legend()
    plt.show()

    return np.std(residuals)

# Plot residuals for Ax flies and get residual std dev
std_residuals_ax = plot_residuals(X_ax, y_ax, model_ax, 'Ax')

# Step 3: Generate random wing values (X) from a normal distribution
def generate_random_x(wing_data, num_flies):
    mean_x = np.mean(wing_data)
    std_x = np.std(wing_data)
    
    random_x = np.random.normal(mean_x, std_x, num_flies).reshape(-1, 1)
    
    # Plot the normally distributed random wing lengths
    plt.figure(figsize=(8, 6))
    plt.hist(random_x, bins=15, color='blue', alpha=0.7, label='Random X values')
    plt.axvline(mean_x, color='red', linestyle='--', label='Mean')
    plt.axvline(mean_x + std_x, color='green', linestyle='--', label='+1 Std Dev')
    plt.axvline(mean_x - std_x, color='green', linestyle='--', label='-1 Std Dev')
    plt.xlabel('Wing Length (cm)')
    plt.ylabel('Frequency')
    plt.title('Random X Values (Wing Length) Distribution')
    plt.legend()
    plt.show()
    
    return random_x

# Generate random wing values for Ax flies
random_x_ax = generate_random_x(ax_wing, 100)

# Step 4: Lift the random points onto the regression line
def lift_to_regression(random_x, model):
    predicted_y = model.predict(random_x)
    
    # Plot random X values and the corresponding points on the regression line
    plt.figure(figsize=(8, 6))
    plt.scatter(random_x, predicted_y, color='orange', label='Lifted Points on Regression Line')
    plt.plot(random_x, predicted_y, color='red', label='Regression Line')
    plt.xlabel('Wing Length (cm)')
    plt.ylabel('Antenna Length (cm)')
    plt.title('Random X values lifted onto the Regression Line')
    plt.legend()
    plt.show()
    
    return predicted_y

# Lift random X values for Ax flies onto the regression line
predicted_y_ax = lift_to_regression(random_x_ax, model_ax)

# Step 5: Create random collection of residuals using the std dev of the residuals
def add_residuals(predicted_y, std_residuals, num_flies):
    residuals = np.random.normal(0, std_residuals, num_flies)
    pseudo_antenna = predicted_y + residuals
    
    # Plot the residuals added to the regression predictions
    plt.figure(figsize=(8, 6))
    plt.scatter(predicted_y, residuals, color='purple', label='Residuals added')
    plt.xlabel('Predicted Antenna Length')
    plt.ylabel('Residuals')
    plt.title('Residuals Added to Regression Predictions')
    plt.legend()
    plt.show()
    
    return pseudo_antenna

# Add residuals for Ax flies
pseudo_antenna_ax = add_residuals(predicted_y_ax, std_residuals_ax, 100)

# Step 6: Generate the formula for random pseudo-flies
def create_pseudo_fly(random_x, pseudo_antenna):
    return random_x, pseudo_antenna

# Example pseudo-flies 
for i in range(100):
    wing, antenna = create_pseudo_fly(random_x_ax[i], pseudo_antenna_ax[i])
    print(f"Pseudo-Fly {i+1}: Wing Length = {wing[0]:.2f}, Antenna Length = {antenna:.2f}")



