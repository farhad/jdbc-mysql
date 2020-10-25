package battleground;

import java.sql.*;

public class SimpleCrud {

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/sql_store?serverTimezone=UTC";
        String userName = "root";
        String password = "lifeisvoid";

        Connection connection = DriverManager.getConnection(url, userName, password);

        displayAllCustomers(connection);

        Customer customer = new Customer();
        customer.setFirstName("Farhad");
        customer.setLastName("Faghihi");
        customer.setAddress("T2R1B4");
        customer.setCity("Calgary");
        customer.setState("AB");
        insertCustomer(connection, customer);

        customer.setCustomerId(getCustomerId(connection, customer));

        displayAllCustomers(connection);

        customer.setFirstName("Tony");
        customer.setLastName("Ezekiel");
        updateCustomer(connection, customer);

        displayAllCustomers(connection);

        deleteCustomer(connection, customer);

        displayAllCustomers(connection);
    }

    private static void displayAllCustomers(Connection connection) {
        System.out.println();
        System.out.println("------------- Displaying all customers -------------");
        System.out.println();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");

            while (resultSet.next()) {
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                System.out.println(firstName + " " + lastName);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static int getCustomerId(Connection connection, Customer customer) {
        try {
            Statement statement = connection.createStatement();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT * FROM customers WHERE first_name=");
            queryBuilder.append("'").append(customer.getFirstName()).append("'");

            ResultSet resultSet = statement.executeQuery(queryBuilder.toString());

            while (resultSet.next()) {
                return resultSet.getInt("customer_id");
            }

            return -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
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

    private static void updateCustomer(Connection connection, Customer customer) {
        System.out.println();
        System.out.println("------------- Updating a customer -------------");
        System.out.println();

        try {
            Statement statement = connection.createStatement();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE customers SET first_name=");
            queryBuilder.append("'").append(customer.getFirstName()).append("'").append(", ");
            queryBuilder.append("last_name=");
            queryBuilder.append("'").append(customer.getLastName()).append("'").append(", ");
            queryBuilder.append("address=");
            queryBuilder.append("'").append(customer.getAddress()).append("'").append(", ");
            queryBuilder.append("city=");
            queryBuilder.append("'").append(customer.getCity()).append("'").append(", ");
            queryBuilder.append("state=");
            queryBuilder.append("'").append(customer.getState()).append("' ");
            queryBuilder.append("WHERE customer_id=");
            queryBuilder.append("'").append(customer.getCustomerId()).append("' ");

            int rowsAffected = statement.executeUpdate(queryBuilder.toString());

            System.out.println(" rows affected: " + rowsAffected);

        } catch (SQLException exception) {
            System.out.println("Inserting customer throwed an exception: ");
            System.out.println(exception.getLocalizedMessage());
        }
    }

    private static void deleteCustomer(Connection connection, Customer customer) {
        System.out.println();
        System.out.println("------------- deleting a customer -------------");
        System.out.println();

        try {
            Statement statement = connection.createStatement();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("DELETE FROM customers WHERE first_name=");
            queryBuilder.append("'").append(customer.getFirstName()).append("'");

            int rowsAffected = statement.executeUpdate(queryBuilder.toString());

            System.out.println(" rows affected: " + rowsAffected);

        } catch (SQLException exception) {
            System.out.println("deleting customer throwed an exception: ");
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
