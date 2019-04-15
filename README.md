# IS4100
Clarity - Agile Cost Management Web App

Package Requirements:
1. NetBeans IDE
2. MySQL (Database)
3. JSF Framework
4. Primefaces 7.0 Jar
5. GlassFish 4.1.1

How to deploy Web Application on localhost:8080

1. Open the IS4100 Java EE Application on NetBeans IDE
2. Right click, clean and build the application to ensure there are no errors
3. Right click, deploy application on GlassFish 4.1.1
4. Once successfully deployed, enter localhost:8080/IS4100-war to enter the website
5. A default account is created upon initialization, with username: manager password: password   #important!


Test Cases:
Assumptions: Cannot Press <Update Task> Twice with the same value.
1.	Login with Username: manager Password: password
2.	Create New Project
  a.	Project Name: Test Project
  b.	Start Date: 15/5/2019
  c.	End Date: 14/6/2019
  d.	Description: Lorem Ipsum
  e.	Budget: 80 000
  f.	Total Points Assigned To Project: 80
3.	Create New Task (Ensure that total points add up to 80 before updating)
  a.	Task 1, 1 point
  b.	Task 2, 3 point
  c.	Task 3, 8 point
  d.	Task 4, 13 point
  e.	Task 5, 21 point
  f.	Task 6, 34 point
4.	Update Task 1
  a.	1 point completed, 1000 spent
5.	Update Task 2
  a.	2 point completed, 3000 spent
6.	Update Task 3
  a.	5 point completed, 9000 spent
7.	Update Task 2
  a.	3 point completed, 4000 spent
8.	Update Task 4
  a.	4 point completed, 4000 spent
9.	Add New Task
  a.	Task 7, 3 point
