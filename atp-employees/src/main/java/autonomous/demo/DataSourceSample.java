package autonomous.demo;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.Properties;

/**
 *    This sample shows you how to use the DataSource API to establish a connection to the Database.
 *    An instance of oracle.jdbc.pool.OracleDataSource doesn't provide
 *    any connection pooling. It's just a connection factory. A connection pool,
 *    such as Universal Connection Pool (UCP), can be configured to use an
 *    instance of oracle.jdbc.pool.OracleDataSource to create connections and
 *    then cache them.
 */
public class DataSourceSample {

    // The recommended format of a connection URL is the long format with the connection descriptor.
    private final static String DB_URL= "jdbc:oracle:thin:@DBNAME_tp?TNS_ADMIN=/tmp/wallets/Wallet_DBNAME";

    // URL with Windows format
    // private final static String DB_URL="jdbc:oracle:thin:@wallet_dbname?TNS_ADMIN=C:\\tmp\\wallets\\wallet_dbname";

    private final static String DB_USER = "JAVA_DEV_USER";
    private final static String DB_PASSWORD = "S0meStr0ngPassw0rd";

    /*
     * The method gets a database connection using
     * oracle.jdbc.pool.OracleDataSource.
     * It also sets some connection level properties, such as,
     * - OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH,
     * - OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, etc.,
     * There are many other connection related properties.
     * Refer to the OracleConnection interface to find more.
     */
    public static void main(String args[]) throws SQLException {
        Properties info = new Properties();
        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");


        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setConnectionProperties(info);

        // With AutoCloseable, the connection is closed automatically.
        try (OracleConnection connection = (OracleConnection) ods.getConnection()) {
            DatabaseMetaData dbmd = connection.getMetaData();
            // Get the JDBC driver name and version
            System.out.println("Driver Name: " + dbmd.getDriverName());
            System.out.println("Driver Version: " + dbmd.getDriverVersion());

            // Print some connection properties
            System.out.println("Default Row Prefetch Value is: " + connection.getDefaultRowPrefetch());
            System.out.println("Database Username is: " + connection.getUserName());
            System.out.println();

            // Perform a database operation
            queryEmployees(connection);
        }
    }

    /*
     * Displays first_name and last_name from the employees table.
     */
    public static void queryEmployees(Connection connection) throws SQLException {
        try (var statement = connection.createStatement()) {
            try (ResultSet resultSet = statement
                    .executeQuery("select first_name, last_name from employees")) {
                System.out.println("FIRST_NAME" + "  " + "LAST_NAME");
                System.out.println("---------------------");
                while (resultSet.next())
                    System.out.println(resultSet.getString(1) + " "
                            + resultSet.getString(2) + " ");
            }
        }
    }
}
