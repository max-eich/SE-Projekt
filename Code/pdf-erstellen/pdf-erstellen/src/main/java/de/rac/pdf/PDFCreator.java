package de.rac.pdf;

import de.rac.pdf.exceptions.PackageCreationException;
import de.rac.pdf.util.ConvertToPdf;
import de.rac.pdf.util.PrintPdf;
import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.DocumentSettingsPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.File;
import java.util.List;

public class PDFCreator {

    /**
     * @param dokument Dokument, in dem die Tabelle erstellt werden soll
     * @param hoehe    Anzahl der Zeilen der Tabelle (mind.  1)
     * @param breite   Anzahl der Spalten der Tabelle (mind. 1)
     * @return Gibt Tabelle zurück
     */
    private static Tbl createTable(WordprocessingMLPackage dokument, int hoehe, int breite) {
        int writableWidthTwips = dokument.getDocumentModel()
                .getSections().get(0).getPageDimensions().getWritableWidthTwips();
        Tbl tbl = TblFactory.createTable(hoehe, breite, writableWidthTwips / breite);
        return tbl;
    }

    /**
     * @return gibt Referenz auf Worddokument zurück
     * @throws PackageCreationException
     */
    private static MainDocumentPart erstelleDokument() throws PackageCreationException {
        // Word Datei erstellen
        WordprocessingMLPackage wordMLPackage = null;

        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();
        } catch (InvalidFormatException e) {
            throw new PackageCreationException("Konnte package nicht erstellen");
        }

        // Aufbau Word-Dokument
        MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
        return mainDocumentPart;
    }

    /**
     * @param content Dokument, wo der Break eingefügt werden soll
     * @throws Docx4JException
     */
    private static void addPageBreak(MainDocumentPart content) throws Docx4JException {
        ObjectFactory objectFactory = new ObjectFactory();
        Br breakObj = objectFactory.createBr();
        breakObj.setType(STBrType.PAGE);

        P paragraph = objectFactory.createP();
        paragraph.getContent().add(breakObj);
        content.getContents().getBody().getContent().add(paragraph);
    }


    /**
     * Erstellt Textfeld und gibt dieses zurück
     *
     * @param message Inhalt des Textfeldes
     * @return
     */
    private static Text erstelleText(String message) {
        ObjectFactory factory = Context.getWmlObjectFactory();
        Text text = factory.createText();
        text.setValue(message);
        return text;
    }

    /**
     * Füllt Zelle einer Tabelle
     *
     * @param text   Inhalt
     * @param table  Die zu füllende Tabelle
     * @param column Die Spalte der Zelle
     * @param row    Die Zeile der Zelle
     */
    private static void addTextToTable(String text, Tbl table, int column, int row) {
        var objectFactory = new ObjectFactory();

        var t = objectFactory.createText();
        t.setValue(text);

        var r = objectFactory.createR();
        r.getContent().add(t);

        var p = objectFactory.createP();
        p.getContent().add(r);

        var currentRow = (Tr) table.getContent().get(--row);
        var currentColumn = (Tc) currentRow.getContent().get(--column);
        currentColumn.getContent().add(p);
    }


    private static void addEmpty(Tbl table, int column, int row) {
        var objectFactory = new ObjectFactory();
        var p = objectFactory.createP();
        var currentRow = (Tr) table.getContent().get(--row);
        var currentColumn = (Tc) currentRow.getContent().get(--column);
        currentColumn.getContent().add(p);
    }

    private static void addTableToCell(Tc zelle, Tbl child) {
        zelle.getContent().add(0, child);
    }


    private static void changeTableBorderColor(Tbl table, String color) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor(color);
    }

    private static void changeTableCellBackgroundColor(Tc cell, String color) {

        ObjectFactory wmlObjectFactory = new ObjectFactory();
        cell.setTcPr(new TcPr());

    }

    private static Tc getTableCell(Tbl tabelle, int row, int column) {
        List<Object> rows = tabelle.getContent();
        Tr selectedrow = (Tr) rows.get(--row);
        List<Object> cells = selectedrow.getContent();
        Tc cell = (Tc) cells.get(--column);
        return cell;
    }


    private static final String OFFICE_VERSION = "15";

    private static void setDocumentVersion(MainDocumentPart mdp) throws Docx4JException {
        DocumentSettingsPart dsp = mdp.getDocumentSettingsPart(true);
        CTCompat compat = new ObjectFactory().createCTCompat();
        dsp.getContents().setCompat(compat);
        compat.setCompatSetting("compatibilityMode", "http://schemas.microsoft.com/office/word", OFFICE_VERSION);
    }


    public static void main(String[] args) throws Exception {


        // Word Datei erstellen
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        // Aufbau Word-Dokument
        MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();


        mainDocumentPart.addStyledParagraphOfText("Title", "Patientendaten");


        // mainDocumentPart.addParagraphOfText("Welcome To Baeldung");
        ObjectFactory factory = Context.getWmlObjectFactory();


     Tbl PatientendatenTabelle = createTable(wordMLPackage, 17, 2);

     addTextToTable("Nachname",PatientendatenTabelle, 1,1);
     addTextToTable("Vorname",PatientendatenTabelle, 1,2);
     addTextToTable("Geschlecht",PatientendatenTabelle, 1,3);
     addTextToTable("Geburtstag",PatientendatenTabelle, 1,4);
     addTextToTable("Alter",PatientendatenTabelle, 1,5);
     addTextToTable("Einlieferung",PatientendatenTabelle, 1,6);
     addTextToTable("Entlassung",PatientendatenTabelle, 1,7);
     addTextToTable("Straße",PatientendatenTabelle ,1,8);
     addTextToTable("Hausnr",PatientendatenTabelle, 1,9);
     addTextToTable("Land",PatientendatenTabelle, 1,10);
     addTextToTable("PLZ",PatientendatenTabelle, 1,11);
     addTextToTable("Ort",PatientendatenTabelle, 1,12);
     addTextToTable("Telefonnummer",PatientendatenTabelle, 1,13);
     addTextToTable("Handynummer",PatientendatenTabelle, 1,14);
     addTextToTable("E-Mail",PatientendatenTabelle, 1,15);
     addTextToTable("Kostenträger",PatientendatenTabelle, 1,16);
     addTextToTable("Versicherungsnummer", PatientendatenTabelle,1, 17);



        //Speichert Parenttabelle im Dokument
        mainDocumentPart.getContent().add(PatientendatenTabelle);


        // Erstellt Abschnitt: Einrichtungen
        mainDocumentPart.addStyledParagraphOfText("Title", "Einrichtungen");
        // Erstellt Einrichtungen Tabelle
        // x = Anzahl der Einrichtungen die in die Tabelle sollen
        int x = 5;
        int tabellenlaenge = x + 1;
        Tbl einrichtungen = createTable(wordMLPackage, tabellenlaenge, 4);

        // Tabellenkopf
        addTextToTable("Name", einrichtungen, 1, 1);
        addTextToTable("Adresse", einrichtungen, 2, 1);
        addTextToTable("Art des Arztes", einrichtungen, 3, 1);
        addTextToTable("Telefonnummer", einrichtungen, 4, 1);

        //Iteriert durch alle Felder der Tabelle

        //i startet bei 2 Wegen dem Tabellenkopf
        for (int i = 2; i <= tabellenlaenge; i++) {
            for (int j = 1; j <= 4; j++) {
                addTextToTable("Tipp", einrichtungen, j, i);
            }
        }
        //speichert Einrichtungen Tabelle im Dokument
        mainDocumentPart.getContent().add(einrichtungen);

        //Fügt einen Seitenumpsrung ein
        addPageBreak(mainDocumentPart);

        // Erstellt Abschnitt: Anamnese
        mainDocumentPart.addStyledParagraphOfText("Title", "Anamnese");

        //Erstellt unsichtbare Hintergrundtabelle
        Tbl anamneseTabelle = createTable(wordMLPackage, 8, 2);

        addTextToTable("Größe in cm", anamneseTabelle, 1, 1);
        addTextToTable("Gewicht in kg", anamneseTabelle, 1, 2);
        addTextToTable("Behinderung", anamneseTabelle, 1, 3);
        addTextToTable("Grad", anamneseTabelle, 1, 4);
        addTextToTable("Endokrinologische Störungen", anamneseTabelle, 1, 5);
        addTextToTable("Mit Adipositas ass. Syptome", anamneseTabelle, 1, 6);
        addTextToTable("Mediakamentenindzierte Adipositas", anamneseTabelle, 1, 7);
        addTextToTable("Weitere chron. Erkrank.", anamneseTabelle, 1, 8);


        mainDocumentPart.getContent().add(anamneseTabelle);


        // Erstellt Abschnitt: Anamnese
        mainDocumentPart.addStyledParagraphOfText("Title", "Krankheitsgeschichte");
        // y = Nummer der passenden Einträge aus der Datenbank
        int y = 5;
        int tabellenlaenge2 = y + 1;
        Tbl krankheitsgeschichteTabelle = createTable(wordMLPackage, tabellenlaenge2, 5);
        addTextToTable("Datum", krankheitsgeschichteTabelle, 1, 1);
        addTextToTable("Typ", krankheitsgeschichteTabelle, 2, 1);
        addTextToTable("ICD-10", krankheitsgeschichteTabelle, 3, 1);
        addTextToTable("Beschreibung", krankheitsgeschichteTabelle, 4, 1);
        addTextToTable("Arzt", krankheitsgeschichteTabelle, 5, 1);

        //i startet bei 2 Wegen dem Tabellenkopf
        for (int i = 2; i <= tabellenlaenge2; i++) {
            for (int j = 1; j <= 5; j++) {
                addTextToTable("Tipp", krankheitsgeschichteTabelle, j, i);
            }
        }


        // Speicher Tabelle "Krankheitsgeschichte"
        mainDocumentPart.getContent().add(krankheitsgeschichteTabelle);

        //Fügt Legende für Tabelle "Krankheitsgeschichte" hinzu.
        mainDocumentPart.addStyledParagraphOfText("text", "D = Diagnose, B = Bemerkung, K = Kommentar");


        // Word Datei speichern
        File exportFile = new File("Temp-Patientenakte.docx");
        wordMLPackage.save(new java.io.File("Temp-Patientenakte.docx"));

        //Konvertierung der .docx zu .pdf via Documents4j-API
        ConvertToPdf.convert();

        //Druuckauftrag
        PrintPdf.print("C:\\Users\\Max\\Documents\\GitHub\\SE-Projekt\\Code\\pdf-erstellen\\pdf-erstellen\\Patientenakte.pdf");


        //Beendet das Programm
        System.exit(0);






        /*
        -umfeldanalyse: Intern/sozial: TeammitgliedeR, Projektleiter?
        -doppelte Leerzeile entfernen
        -Äußere Tabelle undurchsichtig
        - Pagepbreaks einfügen
        -Tabellenfarbe ändern
        -Exeoptions handeln
        -schriftgröße Ändern
        -Textfarbe ändern
        -Schriftart ändern
        -Schriftausrichtung ändern
         */



        // PATIENTENBEREICH NACH SKIZZE.PDF
        /*
        //erstellt Dokumentenbuiler
        Tbl test = createTable(wordMLPackage, 7, 3);


        //Äußere Tabelle durchsichtig machen
        //changeTableBorderColor(test, "white");


        // ERste innere Tabelle (1, 1): Nachname
        Tbl nachnameTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 1, 1), nachnameTabelle);
        addEmpty(test, 1, 1);
        addTextToTable("Nachname", nachnameTabelle, 1, 1);
        addTextToTable("Ebert", nachnameTabelle, 2, 1);

        //Zweite innere Tabelle (1, 2): Vorname
        Tbl vornameTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 1, 2), vornameTabelle);
        addTextToTable("Vorname", vornameTabelle, 1, 1);
        addTextToTable("Joshua-Tim", vornameTabelle, 2, 1);

        //Dritte innere Tabelle (1,3): Geschlecht
        Tbl geschlechtTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 1, 3), geschlechtTabelle);
        addTextToTable("Geschlecht", geschlechtTabelle, 1, 1);
        addTextToTable("D", geschlechtTabelle, 2, 1);

        //Vierte innere Tabelle (2,1): Geburststag
        Tbl geburtstagTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 2, 1), geburtstagTabelle);
        addTextToTable("Geburtstag", geburtstagTabelle, 1, 1);
        addTextToTable("14.07.2003", geburtstagTabelle, 2, 1);

        //Fünfte innere Tabelle (2,2): Alter
        Tbl alterTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 2, 2), alterTabelle);
        addTextToTable("Alter", alterTabelle, 1, 1);
        addTextToTable("18", alterTabelle, 2, 1);

        //Sechste innere Tabelle (3,1): Einlieferung
        Tbl einlieferungTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 3, 1), einlieferungTabelle);
        addTextToTable("Einlieferung", einlieferungTabelle, 1, 1);
        addTextToTable("15.07.2003", einlieferungTabelle, 2, 1);

        //Siebte innere Tabelle (3,2): Entlassung
        Tbl entlassungTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 3, 2), entlassungTabelle);
        addTextToTable("Entlassung", entlassungTabelle, 1, 1);
        addTextToTable("18.06.2022", entlassungTabelle, 2, 1);

        //Siebte innere Tabelle (4,1): Straße
        Tbl strasseTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 4, 1), strasseTabelle);
        addTextToTable("Straße", strasseTabelle, 1, 1);
        addTextToTable("Erlenstrasse", strasseTabelle, 2, 1);

        //Achte innere Tabelle (4,2): Hausnr
        Tbl hausnrTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 4, 2), hausnrTabelle);
        addTextToTable("Hausnr.", hausnrTabelle, 1, 1);
        addTextToTable("88", hausnrTabelle, 2, 1);

        //Neunte innere Tabelle (4,3): Land
        Tbl landTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 4, 3), landTabelle);
        addTextToTable("Land", landTabelle, 1, 1);
        addTextToTable("DE", landTabelle, 2, 1);

        //Neunte innere Tabelle (5, 1): Postleitzahl
        Tbl plzTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 5, 1), plzTabelle);
        addTextToTable("Postleitzahl", plzTabelle, 1, 1);
        addTextToTable("56659", plzTabelle, 2, 1);

        //Zehnte innere Tabelle (5, 2): Ort
        Tbl ortTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 5, 2), ortTabelle);
        addTextToTable("Ort", ortTabelle, 1, 1);
        addTextToTable("Burgbrohl", ortTabelle, 2, 1);

        //Elfte innere Tabelle (6, 1): Telefonnummer
        Tbl tel1Tabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 6, 1), tel1Tabelle);
        addTextToTable("Telefonnr.", tel1Tabelle, 1, 1);
        addTextToTable("0123 456 789", tel1Tabelle, 2, 1);

        //12. innere Tabelle (6, 2): Handynnummer
        Tbl tel2Tabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 6, 2), tel2Tabelle);
        addTextToTable("Mobilnr.", tel2Tabelle, 1, 1);
        addTextToTable("0123 456 789", tel2Tabelle, 2, 1);

        //13. innere Tabelle (6, 3): Email
        Tbl emailTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 6, 3), emailTabelle);
        addTextToTable("E-Mail", emailTabelle, 1, 1);
        addTextToTable("Joshua@Ebert.de", emailTabelle, 2, 1);

        //14. innere Tabelle (7, 1): Kostenträger
        Tbl kostenTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 7, 1), kostenTabelle);
        addTextToTable("Kostenträger", kostenTabelle, 1, 1);
        addTextToTable("MHPlus BKK", kostenTabelle, 2, 1);

        //15. innere Tabelle (7, 2): Versicherungsnummer
        Tbl versTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(test, 7, 2), versTabelle);
        addTextToTable("Versicherungsnummer", versTabelle, 1, 1);
        addTextToTable("0123456789", versTabelle, 2, 1);


                //Speichert Parenttabelle im Dokument
        mainDocumentPart.getContent().add(test);






        //ANAMNESETABELLE NACH SKIZZE.PDF

        changeTableBorderColor(anamneseTabelle, "white");


        // erste innere Tabelle (1, 1): Größe
        Tbl groesseTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 1, 1), groesseTabelle);
        addTextToTable("Groesse (cm)", groesseTabelle, 1, 1);
        addTextToTable("199", groesseTabelle, 2, 1);

        // zweite innere Tabelle (1, 2): Gewicht(Kg)
        Tbl gewichtTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 1, 2), gewichtTabelle);
        addTextToTable("Gewicht (kg)", gewichtTabelle, 1, 1);
        addTextToTable("199", gewichtTabelle, 2, 1);

        // vierte innere Tabelle (2, 1): Behinderung
        Tbl behinderungTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 2, 1), behinderungTabelle);
        addTextToTable("Behinderung", behinderungTabelle, 1, 1);
        addTextToTable("Dement", behinderungTabelle, 2, 1);

        // fünfte innere Tabelle (2, 2): Behinderungsgrad
        Tbl gradTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 2, 2), gradTabelle);
        addTextToTable("Grad", gradTabelle, 1, 1);
        addTextToTable("50%", gradTabelle, 2, 1);

        // sechste innere Tabelle (3, 1): Endokrinologische Störungen
        Tbl stoerungenTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 3, 1), stoerungenTabelle);
        addTextToTable("Endokrinologische Störungen", stoerungenTabelle, 1, 1);
        addTextToTable("Adipositas", stoerungenTabelle, 2, 1);

        // achte innere Tabelle (4, 1): Mit adipositas assoziierte Symptome
        Tbl adipositasTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 4, 1), adipositasTabelle);
        addTextToTable("Mit Adipositas ass. Sympt.", adipositasTabelle, 1, 1);
        addTextToTable("Fettleibigkeit", adipositasTabelle, 2, 1);

        // neunte innere Tabelle (4, 2): Medikamenteninduzierte Adipositas
        Tbl medikamenteTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 4, 2), medikamenteTabelle);
        addTextToTable("Medikamentenindu. Adipositas", medikamenteTabelle, 1, 1);
        addTextToTable("Jep", medikamenteTabelle, 2, 1);

        // zehnte innere Tabelle (5, 1): Weitere Chronische Krankheiten
        Tbl chronischTabelle = createTable(wordMLPackage, 1, 2);
        addTableToCell(getTableCell(anamneseTabelle, 5, 1), chronischTabelle);
        addTextToTable("weitere Chron. Krankh.", chronischTabelle, 1, 1);
        addTextToTable("Neurodermitis", chronischTabelle, 2, 1);
 */
    }
}