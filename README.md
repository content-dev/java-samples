# Java Samples 

These samples show you how to connect to an ATP database from Java.
  
#

## Pull java-samples from GitHub

* Clone project
  ```
  git clone https://github.com/content-dev/java-samples.git
  ```
* Navigate to java-samples directory
  ```
  cd java-samples
  ```


## Explore ATP Employees project

* Navigate to atp-employees project
  ```
  cd atp-employees
  ```
* Add your connection string, and database credentials to `UCPSample.java`

* Run a maven command to package the Java project
  ```
  mvn clean dependency:copy-dependencies package
  ```

* Run the application from the command line
  ```
  java -cp "target/*;target/dependency/*" autonomous.demo.UCPSample
  ```




## SQL scripts for Employee app

* Navigate to `sql-scripts` directory 
* Setup a user for your employees app
  ```
  user-setup.sql
  ```

* Create the employees table for your app
  ```
  table-setup.sql
  ```

* Insert sample data for your app
  ```
  sample-data.sql
  ```