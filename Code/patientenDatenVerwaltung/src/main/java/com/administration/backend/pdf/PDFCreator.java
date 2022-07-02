package com.administration.backend.pdf;

import com.administration.backend.Patient;
import com.administration.backend.pdf.exceptions.PackageCreationException;
import com.administration.backend.pdf.util.ConvertToPdf;
import com.administration.backend.pdf.util.PrintPdf;
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


    public static void drucken(Patient patient) throws Exception {


        // Word Datei erstellen
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        // Aufbau Word-Dokument
        MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();


        mainDocumentPart.addStyledParagraphOfText("Title", "Patientendaten");


        // mainDocumentPart.addParagraphOfText("Welcome To Baeldung");
        ObjectFactory factory = Context.getWmlObjectFactory();


        Tbl PatientendatenTabelle = createTable(wordMLPackage, 16, 2);

        addTextToTable("Nachname", PatientendatenTabelle, 1, 1);
        addTextToTable("Vorname", PatientendatenTabelle, 1, 2);
        addTextToTable("Geschlecht", PatientendatenTabelle, 1, 3);
        addTextToTable("Geburtstag", PatientendatenTabelle, 1, 4);
        addTextToTable("Alter", PatientendatenTabelle, 1, 5);
        addTextToTable("Einlieferung", PatientendatenTabelle, 1, 6);
        addTextToTable("Entlassung", PatientendatenTabelle, 1, 7);
        addTextToTable("Straße", PatientendatenTabelle, 1, 8);
//        addTextToTable("Hausnr", PatientendatenTabelle, 1, 9);
        addTextToTable("PLZ", PatientendatenTabelle, 1, 9);
        addTextToTable("Ort", PatientendatenTabelle, 1, 10);
        addTextToTable("Land", PatientendatenTabelle, 1, 11);
        addTextToTable("Telefonnummer", PatientendatenTabelle, 1, 12);
        addTextToTable("Handynummer", PatientendatenTabelle, 1, 13);
        addTextToTable("E-Mail", PatientendatenTabelle, 1, 14);
        addTextToTable("Kostenträger", PatientendatenTabelle, 1, 15);
        addTextToTable("Versicherungsnummer", PatientendatenTabelle, 1, 16);

        //Tabelle mit Patientendaten fuellen
        addTextToTable(patient.nachname, PatientendatenTabelle, 2, 1);
        addTextToTable(patient.vorname, PatientendatenTabelle, 2, 2);
        addTextToTable(patient.geschlecht.name(), PatientendatenTabelle, 2, 3);
        addTextToTable(patient.geburtsdatum.toString(), PatientendatenTabelle, 2, 4);
        //Es gibt kein Alter
        addTextToTable(patient.unterbringung.einlieferung.toString(), PatientendatenTabelle, 2, 6);
        addTextToTable(patient.unterbringung.entlassung.toString(), PatientendatenTabelle, 2, 7);
        addTextToTable(patient.stamdaten.straße, PatientendatenTabelle, 2, 8);
        addTextToTable(String.valueOf(patient.stamdaten.plz), PatientendatenTabelle, 2, 9);
        addTextToTable(patient.stamdaten.ort, PatientendatenTabelle, 2, 10);
        addTextToTable(patient.stamdaten.land, PatientendatenTabelle, 2, 11);
        addTextToTable(patient.stamdaten.telefon, PatientendatenTabelle, 2, 12);
        addTextToTable(patient.stamdaten.handy, PatientendatenTabelle, 2, 13);
        addTextToTable(patient.stamdaten.eMail, PatientendatenTabelle, 2, 14);
        addTextToTable(patient.stamdaten.kostenträger, PatientendatenTabelle, 2, 15);
        addTextToTable(String.valueOf(patient.stamdaten.versicherungsnummer), PatientendatenTabelle, 2, 16);


        //Speichert Parenttabelle im Dokument
        mainDocumentPart.getContent().add(PatientendatenTabelle);


        // Erstellt Abschnitt: Einrichtungen
        mainDocumentPart.addStyledParagraphOfText("Title", "Einrichtungen");
        // Erstellt Einrichtungen Tabelle
        Tbl einrichtungen = createTable(wordMLPackage, patient.einrichtungen.size()+1, 4);

        // Tabellenkopf
        addTextToTable("Name", einrichtungen, 1, 1);
        addTextToTable("Adresse", einrichtungen, 2, 1);
        addTextToTable("Art des Arztes", einrichtungen, 3, 1);
        addTextToTable("Telefonnummer", einrichtungen, 4, 1);

        //Iteriert durch alle Felder der Tabelle

        //i startet bei 2 Wegen dem Tabellenkopf
        for (int i = 0; i < patient.einrichtungen.size(); i++) {
            // Für die Tabellenzeile
            int j = i+2;
               addTextToTable(String.valueOf(patient.einrichtungen.get(i).name), einrichtungen, 1 ,j);
               addTextToTable(patient.einrichtungen.get(i).adresse, einrichtungen, 2, j);
               addTextToTable(patient.einrichtungen.get(i).art,einrichtungen, 3, j);
               addTextToTable(patient.einrichtungen.get(i).telefonnummer, einrichtungen, 4, j);
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

        // Anamnese-Tabelle fuellen
        addTextToTable(String.valueOf(patient.anamnese.groesse), anamneseTabelle, 2, 1);
        addTextToTable(String.valueOf(patient.anamnese.gewicht),anamneseTabelle, 2, 2);
        if (patient.anamnese.behinderung == true){
            addTextToTable("Ja", anamneseTabelle, 2,3);
            addTextToTable(String.valueOf(patient.anamnese.grad), anamneseTabelle, 2, 4);
        }else{
            addTextToTable("Nein", anamneseTabelle, 2, 3);
            addTextToTable("-", anamneseTabelle, 2, 4);
        }
        addTextToTable(patient.anamnese.endokrinologisch.toString(), anamneseTabelle, 2, 5);
        addTextToTable(patient.anamnese.adipositasSyndrome.toString(), anamneseTabelle, 2, 6);
        addTextToTable(patient.anamnese.adipositasMedikamente.toString(), anamneseTabelle, 2, 7);
        addTextToTable(patient.anamnese.chronischeKrankheiten.toString(), anamneseTabelle, 2,8);


        mainDocumentPart.getContent().add(anamneseTabelle);


        // Erstellt Abschnitt: Anamnese
        mainDocumentPart.addStyledParagraphOfText("Title", "Krankheitsgeschichte");
        // y = Nummer der passenden Einträge aus der Datenbank
        int y = 5;
        int tabellenlaenge2 = y + 1;
        Tbl krankheitsgeschichteTabelle = createTable(wordMLPackage, patient.krankheits.size()+1, 5);
        addTextToTable("Datum", krankheitsgeschichteTabelle, 1, 1);
        addTextToTable("Typ", krankheitsgeschichteTabelle, 2, 1);
        addTextToTable("ICD-10", krankheitsgeschichteTabelle, 3, 1);
        addTextToTable("Beschreibung", krankheitsgeschichteTabelle, 4, 1);
        addTextToTable("Arzt", krankheitsgeschichteTabelle, 5, 1);

        //i startet bei 2 Wegen dem Tabellenkopf
        for (int i = 0; i < patient.krankheits.size(); i++) {
            int j = i + 2;
            addTextToTable(patient.krankheits.get(i).erstellung.toString(), krankheitsgeschichteTabelle, 1, i);
            // Kann keinen Typ finden
            addTextToTable("keinen Typen gefunden", krankheitsgeschichteTabelle, 2, i);
            addTextToTable(patient.krankheits.get(i).icd10, krankheitsgeschichteTabelle, 3, i);
            addTextToTable(patient.krankheits.get(i).beschreibung, krankheitsgeschichteTabelle, 4, i);
            addTextToTable(patient.krankheits.get(i).arzt, krankheitsgeschichteTabelle, 5, i);
        }


        // Speicher Tabelle "Krankheitsgeschichte"
        mainDocumentPart.getContent().add(krankheitsgeschichteTabelle);

        //Fügt Legende für Tabelle "Krankheitsgeschichte" hinzu.
        mainDocumentPart.addStyledParagraphOfText("text", "D = Diagnose, B = Bemerkung, K = Kommentar");


        // Word Datei speichern
        File exportFile = new File("Temp-Patientenakte.docx");
        wordMLPackage.save(new File("Temp-Patientenakte.docx"));

        //Konvertierung der .docx zu .pdf via Documents4j-API
        ConvertToPdf.convert();

        //Druuckauftrag
        PrintPdf.print("src/main/resources/com/administration/pdf/Patientenakte.pdf");

        // Löscht die .docx
        exportFile.delete();

        PrintPdf.deleteFile("src/main/resources/com/administration/pdf/Patientenakte.pdf");

        //Beendet das Programm
        System.exit(0);

    }
}