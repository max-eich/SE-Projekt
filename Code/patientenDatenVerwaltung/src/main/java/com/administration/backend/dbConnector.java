package com.administration.backend;

import java.sql.*;

public class dbConnector {

    private static Connection connect() {
        Connection con = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:src/main/resources/com/administration/database/data.db";
            // create a connection to the database
            con = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    private static void disconnect(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static User checkCard(String card){
        User u = new User();
        System.out.println(card);
        String sql = "SELECT User.name, User.role, User.password FROM User "
                + "INNER JOIN Card on User.id = Card.User_id "
                + "WHERE Card.rfid LIKE '" + card + "'"
                + ";";
        System.out.println(sql);
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
                )
        {
                u.name= rs.getString("name");
                u.role= Role.valueOf(rs.getString("role"));
                u.password= rs.getString("password");
                disconnect(conn);
            System.out.println(u.name);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return u;
    }
}
