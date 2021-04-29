package autonomous.demo;

import autonomous.demo.model.Employee;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *  This sample demonstrates Universal Connection Pool (UCP) as a client
 *  side connection pool and does the following.
 *  1 - Set the connection factory class name to
 *  oracle.jdbc.pool.OracleDataSource before getting a connection.
 *  2 - Set the driver connection properties(e.g.,defaultNChar,includeSynonyms).
 *  3 - Set the connection pool properties(e.g.,minPoolSize, maxPoolSize).
 *  4 - Get the connection and perform some database operations.
 *
 *  TO-DO: Replace the database credentials and database URL
 */

public class UCPSample {

    // The recommended format of a connection URL is the long format with the connection descriptor.
    private final static String DB_URL= "jdbc:oracle:thin:@DBNAME_tp?TNS_ADMIN=/tmp/wallets/Wallet_DBNAME";

    // URL with Windows format
    // private final static String DB_URL="jdbc:oracle:thin:@wallet_dbname?TNS_ADMIN=C:\\tmp\\wallets\\wallet_dbname";

    private final static String DB_USER = "JAVA_DEV_USER";
    private final static String DB_PASSWORD = "S0meStr0ngPassw0rd";

    private final static String CONN_FACTORY_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource";

    /*
     * The sample demonstrates UCP as client side connection pool.
     */
    public static void main(String args[]) throws Exception {

        // Get the PoolDataSource for UCP
        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

        // Set the connection factory first before all other properties
        pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
        pds.setURL(DB_URL);
        pds.setUser(DB_USER);
        pds.setPassword(DB_PASSWORD);
        pds.setConnectionPoolName("JDBC_UCP_DEMO_POOL");

        // Set the initial number of connections to be created when UCP is started.
        // Default is 0.
        pds.setInitialPoolSize(5);

        // Set the minimum number of connections that is maintained by UCP at runtime.
        // Default is 0.
        pds.setMinPoolSize(5);

        // Set the maximum number of connections allowed on the connection pool.
        // Default is Integer.MAX_VALUE (2147483647).
        pds.setMaxPoolSize(20);

        // Set the frequency in seconds to enforce the timeout properties.
        // Applies to inactiveConnectionTimeout(int secs),
        // AbandonedConnectionTimeout(secs)& TimeToLiveConnectionTimeout(int secs).
        // Range of valid values is 0 to Integer.MAX_VALUE.
        // Default is 30secs.
        pds.setTimeoutCheckInterval(5);

        // Set the maximum time, in seconds, that a connection remains available in the connection pool.
        // Default is 0.
        pds.setInactiveConnectionTimeout(10);


        System.out.println("Available connections before checkout: "
                + pds.getAvailableConnectionsCount());
        System.out.println("Borrowed connections before checkout: "
                + pds.getBorrowedConnectionsCount());
        // Get the database connection from UCP.
        try (Connection conn = pds.getConnection()) {
            System.out.println("Available connections after checkout: "
                    + pds.getAvailableConnectionsCount());
            System.out.println("Borrowed connections after checkout: "
                    + pds.getBorrowedConnectionsCount());
            // Perform a database operation
            queryData(conn);
            //doSQLWork(conn);
        }
        System.out.println("Available connections after checkin: "
                + pds.getAvailableConnectionsCount());
        System.out.println("Borrowed connections after checkin: "
                + pds.getBorrowedConnectionsCount());
    }

    public static void queryData(Connection conn) {
            try (PreparedStatement stmt = conn.prepareStatement("Select * from EMPLOYEES ")) {
                List<Employee> employees = queryEmployees(stmt);
                System.out.println("\n---  Records from EMPLOYEES table  --- \n");
                employees.forEach(s -> System.out.println(s));
                System.out.println("\n---  End of Records from EMPLOYEES table  --- \n");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }


    private static List queryEmployees(PreparedStatement preparedStatement) throws SQLException {
        List<Employee> resultList = new ArrayList<Employee>();
        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter strFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {

            LocalDateTime dateTime = LocalDateTime.parse(rs.getString("BIRTH_DATE"), dbFormatter);

            resultList.add(Employee.of(rs.getString("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"),
                    rs.getString("EMAIL"), rs.getString("PHONE"), dateTime.format(strFormatter), rs.getString("TITLE"),
                    rs.getInt("DEPARTMENT")));
        }
        rs.close();
        return resultList;
    }

    private static void insertEmployee(PreparedStatement preparedStatement, Employee employee) throws SQLException {

        preparedStatement.setString(1, employee.getId());
        preparedStatement.setString(2, employee.getFirstName());
        preparedStatement.setString(3, employee.getLastName());
        preparedStatement.setString(4, employee.getEmail());
        preparedStatement.setString(5, employee.getPhone());
        preparedStatement.setDate(6, Date.valueOf(employee.getBirthDate()));
        preparedStatement.setString(7, employee.getTitle());
        preparedStatement.setInt(8, employee.getDepartment());

        preparedStatement.executeQuery();

    }

    /*
     * Creates an EMP table and does an insert, update and select operations on
     * the new table created.
     */
    public static void doSQLWork(Connection conn) {
        try {
            conn.setAutoCommit(false);

            // Create table CLOUD_EMPLOYEES
            try (PreparedStatement stmt = conn.prepareStatement(buildCreateTableSQL())) {
                stmt.execute();
                System.out.println("*** New table CLOUD_EMPLOYEES is created *** ");
            }

            // Insert some records into the table CLOUD_EMPLOYEES
            try (PreparedStatement preparedStatement = conn.prepareStatement(buildInsertSQL())) {
                insertEmployee(preparedStatement, Employee.of("1ASD", "Hugh", "Jast", "hugh.jast@example.com", "730-715-4446", "1970-11-28", "National Data Strategist", 10));
                insertEmployee(preparedStatement, Employee.of("2MNB", "Novella", "Bahringer", "novella.bahringer@example.com", "293-596-3547", "1961-07-25", "Principal Factors Architect", 41));
                insertEmployee(preparedStatement, Employee.of("3ZXC", "Reed", "Hahn", "reed.hahn@example.com", "429-071-2018", "1977-02-05", "Future Directives Facilitator", 31));
                System.out.println("*** Three records were inserted. *** ");
            }

            // Verify the table CLOUD_EMPLOYEES
            try (PreparedStatement stmt = conn.prepareStatement("Select * from CLOUD_EMPLOYEES ")) {
               List<Employee> employees = queryEmployees(stmt);
                System.out.println("--- Retrieved records from CLOUD_EMPLOYEES --- ");
                employees.forEach(s -> System.out.println(s));
                System.out.println("--- END of records from CLOUD_EMPLOYEES --- ");
            }


            System.out.println("\nSuccessfully tested a connection to Autonomous Transaction Processing Database from UCP.");
        }
        catch (SQLException e) {
            System.out.println("UCPSample - "
                    + "doSQLWork()- SQLException occurred : " + e.getMessage());
        }
        finally {
            // Clean-up after everything
            try (PreparedStatement stmt = conn.prepareStatement("drop table CLOUD_EMPLOYEES")) {
                stmt.execute();
                System.out.println("\n*** CLOUD_EMPLOYEES table was dropped. *** \n");
            }
            catch (SQLException e) {
                System.out.println("UCPSample - "
                        + "doSQLWork()- SQLException occurred : " + e.getMessage());
            }
        }
    }





    private static String buildCreateTableSQL(){
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE CLOUD_EMPLOYEES ( ");
        sb.append("   ID VARCHAR2(64) NOT NULL, ");
        sb.append("   FIRST_NAME VARCHAR2(32) NOT NULL, ");
        sb.append("   LAST_NAME VARCHAR2(32), ");
        sb.append("   EMAIL VARCHAR2(64) NOT NULL, ");
        sb.append("   PHONE VARCHAR2(16), ");
        sb.append("   BIRTH_DATE DATE, ");
        sb.append("   TITLE VARCHAR2(64) NOT NULL, ");
        sb.append("   DEPARTMENT NUMBER(8) NOT NULL, ");
        sb.append(" CONSTRAINT CLOUD_EMPLOYEES_PK PRIMARY KEY ");
        sb.append(" (ID, EMAIL) ");
        sb.append("  ENABLE");
        sb.append(" )");

        return sb.toString();
    }

    private static String buildInsertSQL(){
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO CLOUD_EMPLOYEES ( ");
        sb.append("   ID, ");
        sb.append("   FIRST_NAME, ");
        sb.append("   LAST_NAME, ");
        sb.append("   EMAIL, ");
        sb.append("   PHONE, ");
        sb.append("   BIRTH_DATE, ");
        sb.append("   TITLE, ");
        sb.append("   DEPARTMENT ) ");
        sb.append(" VALUES(?,?,?,?,?,?,?,?) ");

        return sb.toString();
    }
}
