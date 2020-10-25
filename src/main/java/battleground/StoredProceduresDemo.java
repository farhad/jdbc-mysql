package battleground;

import java.sql.*;

public class StoredProceduresDemo {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/sql_store?serverTimezone=UTC";
        String userName = "root";
        String password = "lifeisvoid";

        Connection connection = DriverManager.getConnection(url, userName, password);

        CallableStatement spDisplayAllCustomers = connection.prepareCall("{call displayAllCustomers()}");
        displayAllCustomers(spDisplayAllCustomers.executeQuery());

        CallableStatement spIncreasePointsBy = connection.prepareCall("{call increasePointsBy(?)}");
        spIncreasePointsBy.setDouble("increaseAmount", 100.5);
        spIncreasePointsBy.execute();

        CallableStatement spGreetCustomer = connection.prepareCall("{call greetCustomer(?)}");
        String customerName = "Farhad";
        spGreetCustomer.registerOutParameter(1, Types.VARCHAR);
        spGreetCustomer.setString("customerName", customerName);
        spGreetCustomer.execute();
        customerName = spGreetCustomer.getString(1);
        System.out.println(customerName);


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
