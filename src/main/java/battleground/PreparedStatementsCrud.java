package battleground;

import java.sql.*;

public class PreparedStatementsCrud {

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/sql_store?serverTimezone=UTC";
        String userName = "root";
        String password = "lifeisvoid";

        Connection connection = DriverManager.getConnection(url, userName, password);

        searchCustomer(connection, "Ines");

    }

    /**
     * prepared statements can be reused, i.e we can call them with different params
     * we can also use them for insert, delete and update
     */
    private static void searchCustomer(Connection connection, String firstName) {

        System.out.println("------------- Searching for customers named: " + firstName + " -------------");
        try {

            String query = "SELECT * FROM customers WHERE first_name=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);

            ResultSet resultSet = statement.executeQuery();

            int i = 1;

            if (!resultSet.next()) {
                System.out.println();
                System.out.println("no customer found!");
            } else {
                do {
                    System.out.println("------ result #" + i + "------");
                    System.out.println("First Name: " + resultSet.getString(2));
                    System.out.println("Last Name: " + resultSet.getString(3));
                    System.out.println("Address: " + resultSet.getString(6));
                    System.out.println();

                    i++;
                } while (resultSet.next());
            }

        } catch (Exception exception) {
            System.out.println("Search customer threw an exception");
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
