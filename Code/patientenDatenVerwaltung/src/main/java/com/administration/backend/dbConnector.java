package com.administration.backend;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.SortedMap;

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

    public static void setPatientStammdaten(Stamdaten s, int pid, User u){
        /**insert into Stammdaten ( strasse, ort, plz, land, telefon, handy, eMail, kostentraeger, versicherungsnummer,
                aenderung, Patient_id, User_id)
        values ();**/
    }

    private static int findUserID(@NotNull User u){
        int i= 0;

        String sql= "WITH Newest AS ( SELECT referenceID, MAX(aenderung) AS LastUpdate FROM User GROUP BY referenceID) "
                +"SELECT x.referenceID FROM "
                +"(SELECT s.name, s.role, s.password, s.referenceID FROM User s INNER JOIN Newest t ON s.referenceID = t.referenceID AND s.aenderung = t.LastUpdate ) x "
                +"WHERE x.name = '" + u.name+"' "
                +"AND x.role = '" +u.role.toString()+"' "
                +"AND x.password = '"+u.password+"' ;";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if(rs.isBeforeFirst()){
                i=rs.getInt(1);
                disconnect(conn);
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return i;
    }

    public static @NotNull ArrayList<ShortPatient> getPatientlist(@NotNull User u){
        ArrayList<ShortPatient> ps= new ArrayList<>();

        String sql= "WITH Newest AS (SELECT referenceID, MAX(aenderung) AS LastUpdate FROM Patient GROUP BY referenceID) "
                + "SELECT p.patientID, p.vorname, p.nachname, p.geburtstag, p.geschlecht, u.zimmer "
                +" FROM Patient  p "
                +"INNER JOIN Newest t ON p.referenceID = t.referenceID AND p.aenderung = t.LastUpdate "
                +"LEFT JOIN (" +
                "WITH NewestU AS (SELECT Patient_id, MAX(aenderung) AS LastUpdate FROM Unterbringung GROUP BY Patient_id) " +
                "SELECT x.Patient_id, x.zimmer FROM Unterbringung  x INNER JOIN NewestU n ON x.Patient_id = n.Patient_id AND x.aenderung = n.LastUpdate" +
                ") AS u ON u.Patient_id=p.referenceID;";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if(rs.isBeforeFirst()){
                while(rs.next()){
                    ShortPatient s= new ShortPatient();
                    s.name=rs.getString("vorname")+" "+rs.getString("nachname");
                    s.patientID= String.valueOf(rs.getInt("patientID"));
                    if(u.role!=Role.admin)
                        s.geschlecht=rs.getString("geschlecht");
                    else s.geschlecht="";
                    if(u.role!=Role.admin)
                        s.gebDatum=rs.getString("geburtstag");
                    else s.gebDatum="";
                    s.zimmer=rs.getString("zimmer");
                    if(s.zimmer==null){
                        s.zimmer="";
                    }
                    if(s.gebDatum==null) s.gebDatum="";
                    if(s.geschlecht==null) s.geschlecht="";
                    if(s.patientID==null) s.patientID="";
                    if(s.name==null) s.name="";
                    ps.add(s);
                }
            }
            disconnect(conn);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ps;
    }
    public static @NotNull ArrayList<ShortPatient> getPatientlist(@NotNull User u, PatientSearch p){
        ArrayList<ShortPatient> ps= new ArrayList<>();

        String sql= "WITH Newest AS (SELECT referenceID, MAX(aenderung) AS LastUpdate FROM Patient GROUP BY referenceID) "
                + "SELECT p.patientID, p.vorname, p.nachname, p.geburtstag, p.geschlecht, u.zimmer "
                +" FROM Patient  p "
                +"INNER JOIN Newest t ON p.referenceID = t.referenceID AND p.aenderung = t.LastUpdate "
                +"LEFT JOIN (" +
                "WITH NewestU AS (SELECT Patient_id, MAX(aenderung) AS LastUpdate FROM Unterbringung GROUP BY Patient_id) " +
                "SELECT x.Patient_id, x.zimmer FROM Unterbringung  x INNER JOIN NewestU n ON x.Patient_id = n.Patient_id AND x.aenderung = n.LastUpdate" +
                ") AS u ON u.Patient_id=p.referenceID ";
        if(p!=null){
            sql=sql+"WHERE p.patientID = p.patientID ";
            if(p.gebDate!=null)
                sql= sql + "AND p.geburtstag = '"+p.gebDate+"' ";
            if(p.name!=null)
                sql= sql+"AND p.name LIKE '"+p.name+"' ";
            if(p.zimmer!=null)
                sql=sql+"AND u.zimmer LIKE '"+p.zimmer+"' ";
        }
        sql=sql+";";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if(rs.isBeforeFirst()){
                while(rs.next()){
                    ShortPatient s= new ShortPatient();
                    s.name=rs.getString("vorname")+" "+rs.getString("nachname");
                    s.patientID= String.valueOf(rs.getInt("patientID"));
                    if(u.role!=Role.admin)
                    s.geschlecht=rs.getString("geschlecht");
                    else s.geschlecht="";
                    if(u.role!=Role.admin)
                    s.gebDatum=rs.getString("geburtstag");
                    else s.gebDatum="";
                    s.zimmer=rs.getString("zimmer");
                    if(s.zimmer==null){
                        s.zimmer="";
                    }
                    if(s.gebDatum==null) s.gebDatum="";
                    if(s.geschlecht==null) s.geschlecht="";
                    if(s.patientID==null) s.patientID="";
                    if(s.name==null) s.name="";
                    ps.add(s);
                }
            }
            disconnect(conn);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ps;
    }

    public static @NotNull User checkCard(String card) {
        User u = new User();
        String sql = "SELECT User.name, User.role, User.password, CardStatus.status FROM User "
                + "INNER JOIN CardStatus on User.referenceID = CardStatus.User_id "
                + "INNER JOIN Card on CardStatus.Card_id = Card.id "
                + "WHERE Card.rfid LIKE '" + card + "' "
                + "ORDER BY User.aenderung DESC;";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()&&rs.getString("status").equals("ok")) {
                u.name = rs.getString("name");
                u.role = Role.valueOf(rs.getString("role"));
                u.password = rs.getString("password");
            }
            disconnect(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    public static @NotNull Patient getPdfPatient(int pid) {
        Patient p = getPatient(Role.arzt, pid);
        return p;
    }

    public static @NotNull Patient getPatient(Role role, int pid) {
        Patient p = new Patient();

        if (role == Role.techniker) {
            return p;
        }

        String sql = "SELECT Patient.patientID, Patient.vorname, Patient.nachname, Patient.geburtstag, Patient.geschlecht, Patient.id"
                + " FROM Patient "
                + " WHERE Patient.patientID == " + pid + " "
                + " ORDER BY Patient.aenderung DESC;";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            int id = 0;
            if (rs.isBeforeFirst()) {
                p.patientID = rs.getInt("patientID");
                p.vorname = rs.getString("vorname");
                p.nachname = rs.getString("nachname");
                p.geburtsdatum = rs.getTimestamp("geburtstag");
                if (role != Role.admin)
                    p.geschlecht = Geschlecht.valueOf(rs.getString("geschlecht"));
                id = rs.getInt("id");
            }
            disconnect(conn);
            p.stamdaten = getStammdaten(role, id);
            p.unterbringung = getUnterbringung(id);
            if (role == Role.arzt || role == Role.pflege) {
                p.anamnese = getAnamnese(id);
                p.einrichtungen = getEinrichtungen(id);
                p.krankheits = getKrank(role, id);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return p;
    }

    private static @NotNull ArrayList<Krankheit> getKrank(Role role, int id) {
        ArrayList<Krankheit> k = new ArrayList<>();
        String s = "";
        if (role == Role.pflege) {
            s = " AND type= 'k' ";
        }
        String sql = "SELECT * "
                + " FROM Krankheit "
                + " WHERE Patient_id = " + id + " "
                + s
                + " ORDER BY erstellung DESC ;";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Krankheit kr = new Krankheit();
                    kr.type = Type.valueOf(rs.getString(2));
                    kr.icd10 = rs.getString(3);
                    kr.beschreibung = rs.getString(4);
                    kr.erstellung = rs.getTimestamp(5);
                    kr.arzt = rs.getString(6);
                    k.add(kr);
                }
            }
            disconnect(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return k;
    }

    public static @NotNull User userLogin(String name, String password){
        User u = new User();
        String sql= "WITH Newest AS ( SELECT referenceID, MAX(aenderung) AS LastUpdate FROM User GROUP BY referenceID) "+
                " SELECT x.name, x.role, x.password FROM (SELECT s.name, s.role, s.password FROM User s "+
                " INNER JOIN Newest t ON s.referenceID = t.referenceID AND s.aenderung = t.LastUpdate ) x "+
                " WHERE x.name = '"+name+"' "+
                " AND x.password = '"+password+"' ;";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){
            if(rs.isBeforeFirst()){
                u.name=rs.getString(1);
                u.role= Role.valueOf(rs.getString(2));
                u.password=rs.getString(3);
            }
            disconnect(conn);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return u;
    }

    public static @NotNull ArrayList<Einrichtungen> getEinrichtungen(int id) {
        ArrayList<Einrichtungen> e = new ArrayList<Einrichtungen>();

        String sql = "WITH Newest As ( "
                + "SELECT referenceID, MAX(aenderung) as LastReading "
                + "FROM Einrichtungen "
                + "GROUP BY referenceID) "
                + "SELECT s.art,s.name, s.adresse, s.telefonnummer, s.referenceID "
                + "FROM Einrichtungen s "
                + "INNER JOIN Newest t on s.referenceID = t.referenceID and s.aenderung = t.LastReading "
                + "WHERE Patient_id = "+id+";";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Einrichtungen ei = new Einrichtungen();
                    ei.art = rs.getString("art");
                    ei.name = rs.getString(2);
                    ei.adresse = rs.getString(3);
                    ei.telefonnummer = rs.getString(4);
                    ei.referenceID = rs.getInt(5);
                    e.add(ei);
                }
            }
            disconnect(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return e;

    }

    private static @NotNull Anamnese getAnamnese(int id) {
        Anamnese a = new Anamnese();

        String sql = "SELECT groesse, gewicht, behinderung, grad, chronischeKrankheit, id "
                + "FROM Anamnese "
                + "WHERE Patient_id= " + id + " "
                + "ORDER BY aenderung DESC;";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            int a_id = 0;
            if (rs.isBeforeFirst()) {
                a_id = rs.getInt("id");
                a.groesse = rs.getInt("groesse");
                a.gewicht = rs.getDouble("gewicht");
                a.behinderung = rs.getBoolean("behinderung");
                a.grad = rs.getDouble("grad");
                a.chronischeKrankheiten = rs.getString("chronischeKrankheit");
            }
            disconnect(conn);
            a.endokrinologisch = getEndo(a_id);
            a.adipositasMedikamente = getAdipoMed(a_id);
            a.adipositasSyndrome = getAdipoSyn(a_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    private static @NotNull AdipositasSyndrome getAdipoSyn(int a_id) {
        AdipositasSyndrome a = new AdipositasSyndrome();

        String sql = "SELECT * "
                + "FROM AdipositasSyndrom "
                + "WHERE Anamnese_id= " + a_id + ";";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                a.Laurence_Moon_Bardet_Biedel = rs.getBoolean(2);
                a.Prader_Willi = rs.getBoolean(3);
                a.Simpson_Golabi_Behmel = rs.getBoolean(4);
                a.Sotos = rs.getBoolean(5);
                a.Trisomie_21 = rs.getBoolean(6);
                a.Weaver = rs.getBoolean(7);
                a.andere = rs.getString(8);
            }
            disconnect(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    private static @NotNull AdipositasMedikamente getAdipoMed(int a_id) {
        AdipositasMedikamente a = new AdipositasMedikamente();

        String sql = "SELECT * "
                + "FROM AdipositasMedikamente "
                + " WHERE Anamnese_id = " + a_id + ";";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                a.Glukokortikoide = rs.getBoolean(2);
                a.Insulingabe = rs.getBoolean(3);
                a.Valproat = rs.getBoolean(4);
                a.Phenothiazine = rs.getBoolean(5);
                a.andere = rs.getString(6);
            }
            disconnect(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    private static @NotNull Endokrinologisch getEndo(int a_id) {
        Endokrinologisch e = new Endokrinologisch();

        String sql = "SELECT * "
                + "FROM Endokrinologisch "
                + "WHERE Anamnese_id= " + a_id + " ;";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                e.AdipositasBeiHypothyreose = rs.getBoolean("Adipositas bei Hypothyreose");
                e.Cushing_Syndrom = rs.getBoolean("CushingSyndrom");
                e.genetisch_bedingter_Leptinmangel = rs.getBoolean(4);
                e.Kraniopharyngeom = rs.getBoolean(5);
                e.Leptirezeptormutation = rs.getBoolean(6);
                e.MC4_Rezeptormutationen = rs.getBoolean(7);
                e.Morbus_Cushing = rs.getBoolean(8);
                e.POMC_Mutationen = rs.getBoolean(9);
                e.primärerHyperinsulinismusWiedemann_Beckwith = rs.getBoolean(10);
                e.STH_Mangel = rs.getBoolean(11);
            }
            disconnect(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    private static @NotNull Unterbringung getUnterbringung(int id) {
        Unterbringung u = new Unterbringung();

        String sql = "SELECT Unterbringung.zimmer, Unterbringung.entlassung, Unterbringung.einlieferung "
                + "FROM Unterbringung "
                + "WHERE Unterbringung.Patient_id = " + id + " "
                + "ORDER BY Unterbringung.aenderung DESC";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                u.zimmer = rs.getString("zimmer");
                u.entlassung = rs.getString("entlassung");
                u.einlieferung = rs.getString("einlieferung");
            }
            disconnect(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return u;
    }

    private static @NotNull Stamdaten getStammdaten(Role role, int id) {
        Stamdaten s = new Stamdaten();

        String sql = "SELECT Stammdaten.strasse, Stammdaten.ort, Stammdaten.plz, Stammdaten.land, Stammdaten.telefon, Stammdaten.handy, Stammdaten.eMail, Stammdaten.kostentraeger, Stammdaten.versicherungsnummer"
                + " FROM Stammdaten "
                + " WHERE Stammdaten.Patient_id == " + id + " "
                + " ORDER BY Stammdaten.aenderung DESC;";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                if (role != Role.admin) {
                    s.straße = rs.getString("strasse");
                    s.ort = rs.getString("ort");
                    s.plz = Integer.parseInt(rs.getString("plz"));
                    s.land = rs.getString("land");
                    s.telefon = rs.getString("telefon");
                    s.handy = rs.getString("handy");
                    s.kostenträger = rs.getString("kostentraeger");
                    s.versicherungsnummer = rs.getInt("versicherungsnummer");
                }
                s.eMail = rs.getString("eMail");
            }
            disconnect(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return s;
    }

    public static void setPassword(User user, String text) {
        int i = findUserID(user);
        String sql="insert into User ( name, role, password, User_id, referenceID)" +
                "values (?,?,?,?,?);";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.name);
            pstmt.setString(2, user.role.toString());
            pstmt.setString(3,text);
            pstmt.setInt(4,i);
            pstmt.setInt(5,i);
            pstmt.executeUpdate();
            disconnect(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static @NotNull User getUser(@NotNull User user) {
        User u = new User();
        int i = findUserID(user);
        String sql= "SELECT name, role, password FROM User WHERE referenceID =  "+i+" ;";

        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.isBeforeFirst()) {
                u.name = rs.getString("name");
                u.role = Role.valueOf(rs.getString("role"));
                u.password = rs.getString("password");
            }
            disconnect(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    public static void cardLost(@NotNull User user){
        int i = findUserID(user);
        int i1= findCardID(user);
        String sql="INSERT IN TO CardStatus (Card_id, User_id, status, aenderer_id)"
                +" VALUES (?,?,?,?);";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, i1);
            pstmt.setInt(2, i);
            pstmt.setString(3,"lost");
            pstmt.setInt(4,i);
            pstmt.executeUpdate();
            disconnect(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int findCardID(User user) {
        int i=findUserID(user);
        int i1=0;
        String sql="SELECT Card_id FROM CardStatus WHERE User_id = "+i
                +" ORDER BY aenerung DESC;";

        try(
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
                ){
            if(rs.isBeforeFirst()){
                i1=rs.getInt(1);
            }
            disconnect(conn);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return i1;
    }
}
