package com.administration.backend;

import java.sql.*;
import java.util.Date;

public class dbConnector {

    public static Connection connect() {
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
        User u = null;

        String sql = "SELECT User.name, User.role, User.password FROM User, Card "
                + "WHERE Card.rfid == '" + card + "'"
                + " AND Card.User_id == User.id;";

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
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return u;
    }

    public static Patient getPdfPatient(int pid){
        Patient p = getPatient(Role.arzt, pid);
        return p;
    }

    public static Patient getPatient(Role role, int pid){
        Patient p = null;

        String sql = "SELECT Patient.patientID, Patient.vorname, Patient.nachname, Patient.geburtstag, Patient.geschlecht, Patient.id"
                + " FROM Patient "
                + " WHERE Patient.patientID == " + pid + ";";

        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        )
        {
            p.patientID = rs.getInt("patientID");
            p.vorname = rs.getString("vorname");
            p.nachname = rs.getString("nachname");
            p.geburtsdatum =  rs.getDate("gebutstag");
            p.geschlecht = Geschlecht.valueOf(rs.getString("geschlecht"));
            int id = rs.getInt("id");
            p.stamdaten = getStammdaten(role,id);
            disconnect(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p;
    }

    private static Stamdaten getStammdaten(Role role, int id){
        Stamdaten s=null;

        String sql="SELECT Stammdaten.strasse, Stammdaten.ort, Stammdaten.plz, Stammdaten.land, Stammdaten.telefon, Stammdaten.handy, Stammdaten.eMail, Stammdaten.kostentraeger, Stammdaten.versicherungsnummer"
                + " FROM Stammdaten "
                + " WHERE Stammdaten.Patient_id == " + id +";";

        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            s.straße= rs.getString("strasse");
            s.ort = rs.getString("ort");
            s.plz = Integer.parseInt(rs.getString("plz"));
            s.land = rs.getString("land");
            s.telefon = rs.getString("telefon");
            s.handy = rs.getString("handy");
            s.eMail = rs.getString("eMail");
            s.kostenträger = rs.getString("kostentraeger");
            s.versicherungsnummer = rs.getInt("versicherungsnummer");
            disconnect(conn);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return s;
    }
}
