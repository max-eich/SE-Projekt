package com.administration.backend;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class dbConnector {

    public static Connection connect() {
        Connection con = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:src/main/resources/com/administration/database/data.db";
            // create a connection to the database
            con = DriverManager.getConnection(url);
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

    public static @NotNull User checkCard(String card){
        User u = new User();
        String sql = "SELECT User.name, User.role, User.password FROM User "
                + "INNER JOIN CardStatus on User.id = CardStatus.User_id "
                + "INNER JOIN Card on CardStatus.Card_id = Card.id "
                + "WHERE Card.rfid LIKE '" + card + "' "
                + "AND CardStatus.status = 'ok' "
                + ";";
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
                )
        {
            if(rs.isBeforeFirst()){
                u.name= rs.getString("name");
                u.role= Role.valueOf(rs.getString("role"));
                u.password= rs.getString("password");}
                disconnect(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(u);
        return u;
    }

    public static @NotNull Patient getPdfPatient(int pid){
        Patient p = getPatient(Role.arzt, pid);
        return p;
    }

    public static @NotNull Patient getPatient(Role role, int pid){
        Patient p = new Patient();

        if(role==Role.techniker){return p;}

        String sql = "SELECT Patient.patientID, Patient.vorname, Patient.nachname, Patient.geburtstag, Patient.geschlecht, Patient.id"
                + " FROM Patient "
                + " WHERE Patient.patientID == " + pid + " "
                + " ORDER BY Patient.aenderung DESC;";

        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        )
        {
            int id=0;
            if(rs.isBeforeFirst()){
            p.patientID = rs.getInt("patientID");
            p.vorname = rs.getString("vorname");
            p.nachname = rs.getString("nachname");
            p.geburtsdatum =  rs.getTimestamp("geburtstag");
            if(role!=Role.admin)
                p.geschlecht = Geschlecht.valueOf(rs.getString("geschlecht"));
            id = rs.getInt("id");}
            disconnect(conn);
            p.stamdaten = getStammdaten(role,id);
            p.unterbringung=getUnterbringung(id);
            if(role==Role.arzt||role==Role.pflege){
                p.anamnese=getAnamnese(id);
                p.einrichtungen=getEinrichtungen(id);
                p.krankheits=getKrank(role,id);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return p;
    }

    private static ArrayList<Krankheit> getKrank(Role role, int id) {
        ArrayList<Krankheit> k = new ArrayList<>();
        String s= "";
        if(role==Role.pflege){
             s=" AND type= 'k' ";
        }
        String sql="SELECT * "
                + " FROM Krankheit "
                + " WHERE Patient_id = "+id+" "
                + s
                + " ORDER BY erstellung DESC ;";
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Krankheit kr = new Krankheit();
                    kr.type=Type.valueOf( rs.getString(2));
                    kr.icd10 = rs.getString(3);
                    kr.beschreibung=rs.getString(4);
                    kr.erstellung=rs.getTimestamp(5);
                    kr.arzt=rs.getString(6);
                    k.add(kr);
                }
            }
            disconnect(conn);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return k;
    }

    public static @NotNull ArrayList<Einrichtungen> getEinrichtungen(int id) {
        ArrayList<Einrichtungen> e =new ArrayList<Einrichtungen>();

        String sql="WITH Newest As ( "
        +"SELECT referenceID, MAX(aenderung) as LastReading "
        +"FROM Einrichtungen "
        +"GROUP BY referenceID) "
        +"SELECT s.art,s.name, s.adresse, s.telefonnummer "
        +"FROM Einrichtungen s "
        +"INNER JOIN Newest t on s.referenceID = t.referenceID and s.aenderung = t.LastReading "
        +"WHERE Patient_id = 1;";

        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            if(rs.isBeforeFirst()){
                while(rs.next()){
                    Einrichtungen ei = new Einrichtungen();
                    ei.art=rs.getString("art");
                    ei.name=rs.getString(2);
                    ei.adresse=rs.getString(3);
                    ei.telefonnummer=rs.getString(4);
                    e.add(ei);
                }
            }
disconnect(conn);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return e;

    }

    private static @NotNull Anamnese getAnamnese(int id) {
        Anamnese a = new Anamnese();

        String sql="SELECT groesse, gewicht, behinderung, grad, chronischeKrankheit, id "
                + "FROM Anamnese "
                + "WHERE Patient_id= "+id+" "
                + "ORDER BY aenderung DESC;"
                ;
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            int a_id = 0;
            if(rs.isBeforeFirst()){
            a_id = rs.getInt("id");
            a.groesse= rs.getInt("groesse");
            a.gewicht = rs.getDouble("gewicht");
            a.behinderung=rs.getBoolean("behinderung");
            a.grad = rs.getDouble("grad");
            a.chronischeKrankheiten = rs.getString("chronischeKrankheit");}
            disconnect(conn);
            a.endokrinologisch=getEndo(a_id);
            a.adipositasMedikamente=getAdipoMed(a_id);
            a.adipositasSyndrome=getAdipoSyn(a_id);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    private static @NotNull AdipositasSyndrome getAdipoSyn(int a_id) {
        AdipositasSyndrome a = new AdipositasSyndrome();

        String sql="SELECT * "
                + "FROM AdipositasSyndrom "
                + "WHERE Anamnese_id= "+a_id+";"
                ;
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            if(rs.isBeforeFirst()){
                a.Laurence_Moon_Bardet_Biedel=rs.getBoolean(2);
                a.Prader_Willi=rs.getBoolean(3);
                a.Simpson_Golabi_Behmel=rs.getBoolean(4);
                a.Sotos=rs.getBoolean(5);
                a.Trisomie_21=rs.getBoolean(6);
                a.Weaver=rs.getBoolean(7);
                a.andere=rs.getString(8);
            }
            disconnect(conn);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    /**
     * 
     * @param a_id
     * @return
     */
    private static @NotNull AdipositasMedikamente getAdipoMed(int a_id) {
        AdipositasMedikamente a = new AdipositasMedikamente();

        String sql="SELECT * "
                +"FROM AdipositasMedikamente "
                +" WHERE Anamnese_id = "+a_id+";"
                ;
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            if(rs.isBeforeFirst()){
                a.Glukokortikoide=rs.getBoolean(2);
                a.Insulingabe=rs.getBoolean(3);
                a.Valproat=rs.getBoolean(4);
                a.Phenothiazine=rs.getBoolean(5);
                a.andere=rs.getString(6);
            }
            disconnect(conn);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    private static @NotNull Endokrinologisch getEndo(int a_id) {
        Endokrinologisch e = new Endokrinologisch();

        String sql="SELECT * "
                + "FROM Endokrinologisch "
                + "WHERE Anamnese_id= "+a_id+" ;"
                ;
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            if(rs.isBeforeFirst()){
            e.AdipositasBeiHypothyreose=rs.getBoolean("Adipositas bei Hypothyreose");
            e.Cushing_Syndrom=rs.getBoolean("CushingSyndrom");
            e.genetisch_bedingter_Leptinmangel=rs.getBoolean(4);
            e.Kraniopharyngeom=rs.getBoolean(5);
            e.Leptirezeptormutation=rs.getBoolean(6);
            e.MC4_Rezeptormutationen=rs.getBoolean(7);
            e.Morbus_Cushing=rs.getBoolean(8);
            e.POMC_Mutationen=rs.getBoolean(9);
            e.primärerHyperinsulinismusWiedemann_Beckwith=rs.getBoolean(10);
            e.STH_Mangel=rs.getBoolean(11);}
            disconnect(conn);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    private static @NotNull Unterbringung getUnterbringung(int id) {
        Unterbringung u = new Unterbringung();

        String sql="SELECT Unterbringung.zimmer, Unterbringung.entlassung, Unterbringung.einlieferung "
                + "FROM Unterbringung "
                + "WHERE Unterbringung.Patient_id = "+id+" "
                + "ORDER BY Unterbringung.aenderung DESC";
        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            if(rs.isBeforeFirst()){
            u.zimmer= rs.getString("zimmer");
            u.entlassung= rs.getString("entlassung");
            u.einlieferung = rs.getString("einlieferung");}
            disconnect(conn);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return u;
    }

    private static @NotNull Stamdaten getStammdaten(Role role, int id){
        Stamdaten s=new Stamdaten();

        String sql="SELECT Stammdaten.strasse, Stammdaten.ort, Stammdaten.plz, Stammdaten.land, Stammdaten.telefon, Stammdaten.handy, Stammdaten.eMail, Stammdaten.kostentraeger, Stammdaten.versicherungsnummer"
                + " FROM Stammdaten "
                + " WHERE Stammdaten.Patient_id == " + id +" "
                + " ORDER BY Stammdaten.aenderung DESC;";

        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            if(rs.isBeforeFirst()){
            if(role!=Role.admin){
            s.straße= rs.getString("strasse");
            s.ort = rs.getString("ort");
            s.plz = Integer.parseInt(rs.getString("plz"));
            s.land = rs.getString("land");
            s.telefon = rs.getString("telefon");
            s.handy = rs.getString("handy");
            s.kostenträger = rs.getString("kostentraeger");
            s.versicherungsnummer = rs.getInt("versicherungsnummer");}
            s.eMail = rs.getString("eMail");}
            disconnect(conn);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return s;
    }
}
