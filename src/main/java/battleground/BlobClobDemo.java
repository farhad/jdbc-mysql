package battleground;

import java.io.*;
import java.sql.*;

public class BlobClobDemo {
    public static void main(String[] args) throws SQLException, IOException {
        String url = "jdbc:mysql://localhost:3306/sql_store?serverTimezone=UTC";
        String userName = "root";
        String password = "lifeisvoid";

        Connection connection = DriverManager.getConnection(url, userName, password);

        /**
         * reading the file as InputStream from classLoader didn't work. todo: why?
         */
        File file = new File("src/main/resources/resume.pdf");
        FileInputStream fileInputStream = new FileInputStream(file);

        String query = "UPDATE customers SET resume=? WHERE first_name='Elka'";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setBinaryStream(1, fileInputStream);

        System.out.println("rowAffected -> " + statement.executeUpdate());


        /**
         * reading blob from database
         */
        Statement selectResume = connection.createStatement();
        ResultSet resultSet = selectResume.executeQuery("SELECT resume FROM customers where first_name='Elka'");

        File file1 = new File("src/main/resources/resume-from-db.pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(file1);

        if (resultSet.next()) {
            InputStream inputStream = resultSet.getBinaryStream(1);

            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }

        }

    }
}
