import numpy as np
import matplotlib.pyplot as plt

# constants
g = 9.81  # m/s^2
Cd = 0.5  # Drag coefficient (assumed)
A = 10.0  # cross-sectional area (m^2)
rho0 = 1.225  # air density at sea level (kg/m^3)
h_scale = 8500  # scale height for atmosphere (m)

# air density
def air_density(h):
    return rho0 * np.exp(-h / h_scale)

# Function to calculate drag force
def drag_force(v, h):
    return 0.5 * air_density(h) * v**2 * Cd * A

# init conditions
m = 300000  # init mass of rocket (kg)
T = 3.5e6   # thrust (N)
burn_rate = 2500  # kg/s
v = 0       # init velocity (m/s)
h = 0       # init height (m)
dt = 0.01    # time step (s)
time = 0    # init time (s)

#init lists
velocities = []
heights = []
times = []

# Simulation loop
while h >= 0 and m > 50000:  # if rocket go kaboom or fuel gone
    Fg = m * g
    Fd = drag_force(v, h)
    a = (T - Fg - Fd) / m  #acc
    
    # update velocity/height
    v += a * dt
    h += v * dt
    
    # mass changing bc of fuel
    m -= burn_rate * dt
    
    # Update time
    time += dt
    
    # Store results
    velocities.append(v)
    heights.append(h)
    times.append(time)
    
    #stage sep
    if m <= 150000 and m > 100000:  # conditional 
        T = 2.0e6  #new thrust
        burn_rate = 1500  #new br


    if time in {1, 10, 20, 30, 50, 75, 100}:
        print(v)

# plot results
plt.figure(figsize=(10, 5))
plt.subplot(1, 2, 1)
plt.plot(times, velocities)
plt.xlabel('Time (s)')
plt.ylabel('Velocity (m/s)')
plt.title('Velocity vs Time')

plt.subplot(1, 2, 2)
plt.plot(times, heights)
plt.xlabel('Time (s)')
plt.ylabel('Height (m)')
plt.title('Height vs Time')

plt.tight_layout()
plt.show()
