package battleground;

import java.sql.*;

public class TransactionsDemo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sql_store?serverTimezone=UTC";
        String userName = "root";
        String password = "lifeisvoid";

        Connection connection = DriverManager.getConnection(url, userName, password);

        connection.setAutoCommit(false);

        displayAllCustomers(connection);

        Customer customer = new Customer();
        customer.setFirstName("George");
        customer.setLastName("Floyd");
        customer.setAddress("MNSP");
        customer.setCity("MP");
        customer.setState("MP");

        insertWithoutCommit(connection, customer);

        displayAllCustomers(connection);

        insertWithCommit(connection, customer);

        displayAllCustomers(connection);

        connection.setAutoCommit(true); // don't forget to change it back to true
    }

    private static void displayAllCustomers(Connection connection) {
        System.out.println();
        System.out.println("------------- Displaying all customers from a stored procedure -------------");
        System.out.println();

        try {

            CallableStatement spDisplayAllCustomers = connection.prepareCall("{call displayAllCustomers()}");
            ResultSet resultSet = spDisplayAllCustomers.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                System.out.println(firstName + " " + lastName);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insertWithoutCommit(Connection connection, Customer customer) throws SQLException {
        System.out.println("--------- inserting and calling rollback at the end");
        insertCustomer(connection, customer);
        connection.rollback(); // if you don't call rollback data is already commited to the database!
    }

    private static void insertWithCommit(Connection connection, Customer customer) throws SQLException {
        System.out.println("--------- inserting and calling commit at the end");
        insertCustomer(connection, customer);
        connection.commit();
    }

    private static void insertCustomer(Connection connection, Customer customer) {
        System.out.println();
        System.out.println("------------- Inserting a new customer -------------");
        System.out.println();

        try {
            Statement statement = connection.createStatement();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO customers ( first_name, last_name, address, city, state) VALUES (");
            queryBuilder.append("'").append(customer.getFirstName()).append("'").append(", ");
            queryBuilder.append("'").append(customer.getLastName()).append("'").append(", ");
            queryBuilder.append("'").append(customer.getAddress()).append("'").append(", ");
            queryBuilder.append("'").append(customer.getCity()).append("'").append(", ");
            queryBuilder.append("'").append(customer.getState()).append("'").append(")");

            int rowsAffected = statement.executeUpdate(queryBuilder.toString());

            System.out.println(" rows affected: " + rowsAffected);

        } catch (SQLException exception) {
            System.out.println("Inserting customer throwed an exception: ");
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
