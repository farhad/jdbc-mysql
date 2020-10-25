package battleground;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class PropertiesFileDemo {
    public static void main(String[] args) throws IOException, SQLException {
        FileInputStream fileInputStream = new FileInputStream(new File("src/main/resources/connection.properties"));

        Properties properties = new Properties();
        properties.load(fileInputStream);

        String url = properties.getProperty("url");
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");

        Connection connection = DriverManager.getConnection(url, userName, password);

        CallableStatement spDisplayAllCustomers = connection.prepareCall("{call displayAllCustomers()}");
        displayAllCustomers(spDisplayAllCustomers.executeQuery());
    }

    private static void displayAllCustomers(ResultSet resultSet) {
        System.out.println();
        System.out.println("------------- Displaying all customers from a stored procedure -------------");
        System.out.println();

        try {

            while (resultSet.next()) {
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                System.out.println(firstName + " " + lastName);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
