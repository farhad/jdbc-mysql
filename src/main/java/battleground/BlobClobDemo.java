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
         * using BLOB column demo
         */
        blob(connection);

        /**
         * using CLOB column demo
         */
        clob(connection);

    }

    private static void blob(Connection connection) throws IOException, SQLException {

        /**
         * reading the file as InputStream from classLoader didn't work. todo: why?
         */
        File file = new File("src/main/resources/resume.pdf");
        FileInputStream fileInputStream = new FileInputStream(file);

        /**
         * updating blob column in db
         */
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

    private static void clob(Connection connection) throws SQLException, IOException {
        String clobQuery = "UPDATE customers SET bio=? WHERE first_name='Elka'";
        PreparedStatement statement = connection.prepareStatement(clobQuery);

        /**
         * Updating clob column in db
         */
        File file = new File("src/main/resources/sample.txt");
        FileReader fileReader = new FileReader(file);
        statement.setCharacterStream(1, fileReader);

        System.out.println("rowAffected -> " + statement.executeUpdate());

        /**
         * reading clob column in db
         */
        File file1 = new File("src/main/resources/bio-from-db.txt");
        FileWriter fileWriter = new FileWriter(file1);

        Statement selectResume = connection.createStatement();
        ResultSet resultSet = selectResume.executeQuery("SELECT bio FROM customers where first_name='Elka'");

        while (resultSet.next()) {
            Reader reader = resultSet.getCharacterStream("bio");

            int theChar;
            while ((theChar = reader.read()) > 0) {
                fileWriter.write(theChar);
            }
        }

        fileWriter.flush();

    }
}
