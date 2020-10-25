package battleground;

import java.sql.*;

public class StoredProceduresDemo {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/sql_store?serverTimezone=UTC";
        String userName = "root";
        String password = "lifeisvoid";

        Connection connection = DriverManager.getConnection(url, userName, password);

        CallableStatement callableStatement = connection.prepareCall("{call displayAllCustomers()}");
        displayAllCustomers(callableStatement.executeQuery());

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