import pandas as pd
import matplotlib.pyplot as plt
import statsmodels.api as sm

# Load the Excel file
file_path = '/Users/arnavmaniar/Documents/Supplementals/projects/UNC Salary/Salary Data Export.xlsx'
excel_data = pd.ExcelFile(file_path)

# Load the data from the 'Salary Data' sheet
salary_data = pd.read_excel(file_path, sheet_name='Salary Data')

# Standardize column names
salary_data.columns = salary_data.columns.str.strip().str.lower().str.replace(' ', '_')

# Convert 'initial_hire_date' to datetime format
salary_data['initial_hire_date'] = pd.to_datetime(salary_data['initial_hire_date'], errors='coerce')

# Drop the 'init' column
salary_data.drop(columns=['init'], inplace=True)

# Descriptive statistics for numerical columns
descriptive_stats = salary_data.describe()
print("Descriptive Statistics:")
print(descriptive_stats)

# Salary distribution
plt.figure(figsize=(10, 6))
plt.hist(salary_data['employee_annual_base_salary'], bins=30, color='skyblue', edgecolor='black')
plt.title('Distribution of Annual Base Salaries')
plt.xlabel('Annual Base Salary')
plt.ylabel('Frequency')
plt.grid(axis='y')
plt.show()

# Age distribution
plt.figure(figsize=(10, 6))
plt.hist(salary_data['age'], bins=20, color='lightgreen', edgecolor='black')
plt.title('Distribution of Ages')
plt.xlabel('Age')
plt.ylabel('Frequency')
plt.grid(axis='y')
plt.show()

# Job category analysis: Distribution of salaries and ages across job categories
job_category_stats = salary_data.groupby('job_category').agg({
    'employee_annual_base_salary': ['mean', 'median', 'count'],
    'age': ['mean', 'median']
}).reset_index()

# Flatten the multi-index columns
job_category_stats.columns = ['_'.join(col).strip() for col in job_category_stats.columns.values]
print("Job Category Analysis:")
print(job_category_stats)

# Correlation analysis between age and salary
correlation = salary_data[['age', 'employee_annual_base_salary']].corr()
print("Correlation between Age and Salary:")
print(correlation)

# Extract year from initial hire date
salary_data['hire_year'] = salary_data['initial_hire_date'].dt.year

# Calculate average salary by hire year
salary_trends = salary_data.groupby('hire_year')['employee_annual_base_salary'].mean().reset_index()

# Plot salary trends over hire years
plt.figure(figsize=(12, 6))
plt.plot(salary_trends['hire_year'], salary_trends['employee_annual_base_salary'], marker='o', linestyle='-', color='blue')
plt.title('Average Salary Trends Over Hire Years')
plt.xlabel('Hire Year')
plt.ylabel('Average Annual Base Salary')
plt.grid(True)
plt.show()

# Department analysis: Distribution of salaries across departments
department_stats = salary_data.groupby('employee_home_department').agg({
    'employee_annual_base_salary': ['mean', 'median', 'count'],
    'age': ['mean', 'median']
}).reset_index()

# Flatten the multi-index columns
department_stats.columns = ['_'.join(col).strip() for col in department_stats.columns.values]
print("Department Analysis:")
print(department_stats)

# Plot average salary by department
plt.figure(figsize=(12, 8))
department_stats_sorted = department_stats.sort_values(by='employee_annual_base_salary_mean', ascending=False)
plt.barh(department_stats_sorted['employee_home_department_'], department_stats_sorted['employee_annual_base_salary_mean'], color='orange')
plt.xlabel('Average Annual Base Salary')
plt.title('Average Salary by Department')
plt.grid(axis='x')
plt.show()

# Salary trends by department over hire years
department_trends = salary_data.groupby(['hire_year', 'employee_home_department'])['employee_annual_base_salary'].mean().unstack()

# Plot salary trends by department
plt.figure(figsize=(14, 8))
department_trends.plot(ax=plt.gca())
plt.title('Average Salary Trends by Department Over Hire Years')
plt.xlabel('Hire Year')
plt.ylabel('Average Annual Base Salary')
plt.legend(title='Department', bbox_to_anchor=(1.05, 1), loc='upper left')
plt.grid(True)
plt.show()

# Deeper analysis of age and salary within each job category
job_category_age_salary = salary_data.groupby('job_category').agg({
    'employee_annual_base_salary': ['mean', 'median', 'min', 'max'],
    'age': ['mean', 'median', 'min', 'max']
}).reset_index()

# Flatten the multi-index columns
job_category_age_salary.columns = ['_'.join(col).strip() for col in job_category_age_salary.columns.values]
print("Job Category Age and Salary Analysis:")
print(job_category_age_salary)

# Plot average age by job category
plt.figure(figsize=(12, 8))
job_category_age_salary_sorted = job_category_age_salary.sort_values(by='age_mean', ascending=False)
plt.barh(job_category_age_salary_sorted['job_category_'], job_category_age_salary_sorted['age_mean'], color='purple')
plt.xlabel('Average Age')
plt.title('Average Age by Job Category')
plt.grid(axis='x')
plt.show()

# Plot average salary by job category
plt.figure(figsize=(12, 8))
job_category_salary_sorted = job_category_age_salary.sort_values(by='employee_annual_base_salary_mean', ascending=False)
plt.barh(job_category_salary_sorted['job_category_'], job_category_salary_sorted['employee_annual_base_salary_mean'], color='green')
plt.xlabel('Average Annual Base Salary')
plt.title('Average Salary by Job Category')
plt.grid(axis='x')
plt.show()

# Gender analysis: Distribution of salaries and ages by gender
# Assuming there's a 'gender' column in the dataset
if 'gender' in salary_data.columns:
    gender_stats = salary_data.groupby('gender').agg({
        'employee_annual_base_salary': ['mean', 'median', 'count'],
        'age': ['mean', 'median']
    }).reset_index()

    # Flatten the multi-index columns
    gender_stats.columns = ['_'.join(col).strip() for col in gender_stats.columns.values]
    print("Gender Analysis:")
    print(gender_stats)

    # Plot average salary by gender
    plt.figure(figsize=(8, 6))
    plt.bar(gender_stats['gender_'], gender_stats['employee_annual_base_salary_mean'], color=['blue', 'pink'])
    plt.xlabel('Gender')
    plt.ylabel('Average Annual Base Salary')
    plt.title('Average Salary by Gender')
    plt.grid(axis='y')
    plt.show()

# Regression analysis to understand factors influencing salary
# Let's use age and hire_year as independent variables
X = salary_data[['age', 'hire_year']]
X = sm.add_constant(X)
y = salary_data['employee_annual_base_salary']

# Fit the regression model
model = sm.OLS(y, X).fit()
print("Regression Analysis Summary:")
print(model.summary())

# Outlier detection for salary
salary_data['salary_zscore'] = (salary_data['employee_annual_base_salary'] - salary_data['employee_annual_base_salary'].mean()) / salary_data['employee_annual_base_salary'].std()

# Identify outliers
salary_outliers = salary_data[salary_data['salary_zscore'].abs() > 3]
print("Salary Outliers:")
print(salary_outliers[['last_name', 'first_name', 'employee_annual_base_salary', 'salary_zscore']])

# Outlier detection for age
salary_data['age_zscore'] = (salary_data['age'] - salary_data['age'].mean()) / salary_data['age'].std()

# Identify outliers
age_outliers = salary_data[salary_data['age_zscore'].abs() > 3]
print("Age Outliers:")
print(age_outliers[['last_name', 'first_name', 'age', 'age_zscore']])

# Identify top earners and their roles
top_earners = salary_data.nlargest(10, 'employee_annual_base_salary')
print("Top Earners and Their Roles:")
print(top_earners[['last_name', 'first_name', 'job_category', 'employee_annual_base_salary']])

# Salary data by location (assuming there's a 'location' column in the dataset)
if 'location' in salary_data.columns:
    location_stats = salary_data.groupby('location').agg({
        'employee_annual_base_salary': ['mean', 'median', 'count'],
        'age': ['mean', 'median']
    }).reset_index()

    # Flatten the multi-index columns
    location_stats.columns = ['_'.join(col).strip() for col in location_stats.columns.values]
    print("Location Analysis:")
    print(location_stats)

    # Plot average salary by location
    plt.figure(figsize=(12, 8))
    location_stats_sorted = location_stats.sort_values(by='employee_annual_base_salary_mean', ascending=False)
    plt.barh(location_stats_sorted['location_'], location_stats_sorted['employee_annual_base_salary_mean'], color='teal')
    plt.xlabel('Average Annual Base Salary')
    plt.title('Average Salary by Location')
    plt.grid(axis='x')
    plt.show()
