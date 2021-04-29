# Java Samples - Connecting to Oracle Autonomous Transaction Processing (ATP)

These samples show you how to connect to an ATP database from Java.

## Prerequisites

1. JDK 11
    * [Download Java SE Development Kit](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html "Java SE 11 Downloads page")
    * Verify if installed
        ```
        java -version
        ```
2. Oracle JDBC driver for IntelliJ IDE (Skip this step if you use a different IDE) 
    * [Download  JDBC Driver & UCP](https://www.oracle.com/database/technologies/appdev/jdbc-ucp-19c-downloads.html "Oracle JDBC and UCP 19c Download page")
    * Select the [ojdbc10-full.tar.gz](https://www.oracle.com/database/technologies/appdev/jdbc-ucp-19c-downloads.html#license-lightbox "ojdbc10-full")
3. For brand-new Maven projects, add the JDBC dependencies to your `pom.xml` file.
    ```
    <properties>
        <oracle.jdbc.version>19.10.0.0</oracle.jdbc.version>
    </properties>
      
    <dependencies>
        <!-- From jdbc version 19.7 onwards developers “pick-and-choose” the JDBC Artifacts from Maven -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc10</artifactId>
            <version>${oracle.jdbc.version}</version>
        </dependency>
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ucp</artifactId>
            <version>${oracle.jdbc.version}</version>
        </dependency>
        <dependency>
            <groupId>com.oracle.database.security</groupId>
            <artifactId>oraclepki</artifactId>
            <version>${oracle.jdbc.version}</version>
        </dependency>
        <dependency>
            <groupId>com.oracle.database.security</groupId>
            <artifactId>osdt_core</artifactId>
            <version>${oracle.jdbc.version}</version>
        </dependency>
        <dependency>
            <groupId>com.oracle.database.security</groupId>
            <artifactId>osdt_cert</artifactId>
            <version>${oracle.jdbc.version}</version>
        </dependency>
    </dependencies> 
      ```
   
4. Access to an ATP instance and its security credentials (Oracle ATP Wallet)
    * [Download Client Credentials (Wallets)](https://docs.oracle.com/en/cloud/paas/autonomous-database/adbsa/connect-download-wallet.html#GUID-B06202D2-0597-41AA-9481-3B174F75D4B1 "ATP Wallet doc")
    * Your database has the  [EMPLOYEES table](../sql-scripts/table-setup.sql) with some [sample data](../sql-scripts/sample-data.sql) to query.


  
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


## Explore ATP Employees Java project

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



## SQL scripts for employee app

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