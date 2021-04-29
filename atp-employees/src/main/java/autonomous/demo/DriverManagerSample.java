package autonomous.demo;

import java.sql.*;
import java.util.Properties;

public class DriverManagerSample {

    // The recommended format of a connection URL is the long format with the connection descriptor.
    private final static String DB_URL= "jdbc:oracle:thin:@DB4JAVALIVE_tp?TNS_ADMIN=/tmp/wallets/Wallet_DB4JAVALIVE";

    // URL with Windows format
    // private final static String DB_URL="jdbc:oracle:thin:@wallet_dbname?TNS_ADMIN=C:\\tmp\\wallets\\wallet_dbname";

    private final static String DB_USER = "JAVA_DEV_USER";
    private final static String DB_PASSWORD = "S0meStr0ngPassw0rd";


    public static void main(String... args){

        try {
            Properties info = new Properties();
            info.put ("user", DB_USER);
            info.put ("password",DB_PASSWORD);
            info.put ("defaultRowPrefetch","15");

            Connection conn = DriverManager.getConnection(DB_URL, info);

            printEmployees(conn);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void printEmployees(Connection connection) throws SQLException {
        // Statement and ResultSet are AutoCloseable and closed automatically.
        try (Statement statement = connection.createStatement()) {
            try (var resultSet = statement
                    .executeQuery("select first_name, last_name from employee")) {
                System.out.println("FIRST_NAME" + "  " + "LAST_NAME");
                System.out.println("---------------------");
                while (resultSet.next())
                    System.out.println(resultSet.getString(1) + " "
                            + resultSet.getString(2) + " ");
            }
        }
    }

}
