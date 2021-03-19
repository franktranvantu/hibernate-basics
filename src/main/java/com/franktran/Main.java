package com.franktran;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        // TODO: Create a DB connection
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://172.21.3.49:3306/hibernate_basics", "bean", "beandev@123")) {

            // TODO: Create a SQL statement
            Statement statement = connection.createStatement();

            // TODO: Create a DB table
            statement.executeUpdate("DROP TABLE IF EXISTS contacts");
            statement.executeUpdate("CREATE TABLE contacts(id INT PRIMARY KEY AUTO_INCREMENT, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(50), phone INT(10))");

            // TODO: Insert a couple contacts
            Contact frank = new Contact("Frank", "Tran", "franktran@gmail.com", 0123456711);
            Contact henry = new Contact("Henry", "Tran", "henrytran@gmail.com", 0123444555);
            Contact bean = new Contact("Bean", "Nguyen", "beannguyen@gmail.com", 0123666777);
            save(frank, statement);
            save(henry, statement);
            save(bean, statement);

            // TODO: Fetch all the records from the contacts table
            ResultSet rs = statement.executeQuery("SELECT * FROM contacts");

            // TODO: Iterate over the ResultSet & display contact info
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                System.out.printf("%s %s (%d)%n", firstName, lastName, id);
            }

        } catch (SQLException ex) {
            // Display connection or query errors
            System.err.printf("There was a database error: %s%n",ex.getMessage());
        }
    }

    public static void save(Contact contact, Statement statement) throws SQLException {
        // Compose the query
        String sql = "INSERT INTO contacts (first_name, last_name, email, phone) VALUES ('%s', '%s', '%s', %d)";
        sql = String.format(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());

        // Execute the query
        statement.executeUpdate(sql);
    }
}
