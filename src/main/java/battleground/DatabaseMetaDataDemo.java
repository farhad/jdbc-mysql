package battleground;

import java.sql.*;

public class DatabaseMetaDataDemo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sql_store?serverTimezone=UTC";
        String userName = "root";
        String password = "lifeisvoid";

        Connection connection = DriverManager.getConnection(url, userName, password);
        DatabaseMetaData metaData = connection.getMetaData();

        System.out.println(metaData.getDriverMajorVersion() + "." + metaData.getDatabaseMinorVersion());
        System.out.println(metaData.getDatabaseProductName());
        System.out.println(metaData.getDatabaseProductVersion());
        System.out.println(metaData.getJDBCMajorVersion() + "." + metaData.getJDBCMinorVersion());

        /**
         * returns the names of all schemas in mysql
         */
        ResultSet resultSet = metaData.getCatalogs();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        CallableStatement spDisplayAllCustomers = connection.prepareCall("{call displayAllCustomers()}");
        ResultSet customersRs = spDisplayAllCustomers.executeQuery();
        ResultSetMetaData resultSetMetaData = customersRs.getMetaData();

        /**
         * with jdbc almost all indices are 1-based instead of 0-based
         */
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            System.out.println("column name -> " + resultSetMetaData.getColumnName(i));
            System.out.println("column type -> " + resultSetMetaData.getColumnTypeName(i));
            System.out.println("column isNullable -> " + resultSetMetaData.isNullable(i));
            System.out.println("column is AutoIncrement -> " + resultSetMetaData.isAutoIncrement(i));
        }

    }
}
