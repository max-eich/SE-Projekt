package com.administration.backend.pdf;

import com.administration.backend.Patient;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;

public class Drucken {
    private final int MIN_DISTANCE = 10;
    private byte[] pdfFile;

    public static BaseColor colorPick(String farbe) {
        BaseColor baseColor;
        if ("white".equals(farbe)) {
            baseColor = new BaseColor(255, 255, 255);
        } else if ("hellblau".equals(farbe)) {
            baseColor = new BaseColor(10, 189, 227);
        } else if ("black".equals(farbe)) {
            baseColor = new BaseColor(0, 0, 0);
        } else {
            baseColor = new BaseColor(255, 255, 255);
        }
        return baseColor;
    }

    private static void changeColor(PdfPCell zelle, String borderColor, String backgroundColor) {
        zelle.setBorderColor(colorPick(borderColor));
        zelle.setBackgroundColor(colorPick(backgroundColor));
    }

    private static PdfPTable erstelleTabelle(int breite) {
        return new PdfPTable(breite);
    }

    ;

    public static PdfPCell zelle(String content, Font font, String backgroundColor, String borderColor) {
        PdfPCell zelle = new PdfPCell(new Phrase(content, font));
        zelle.setBorderWidth(0.8f);
        changeColor(zelle, borderColor, backgroundColor);
        return zelle;
    }


    private static void setRow2(PdfPTable tabelle, String content1, String content2, Font font) {
        tabelle.addCell(zelle(content1, font, "white", "black"));
        tabelle.addCell(zelle(content2, font, "white", "black"));
    }

    ;

    private static void setRow4(PdfPTable tabelle, String content1, String content2, String content3, String content4, Font font) {
        tabelle.addCell(zelle(content1, font, "white", "black"));
        tabelle.addCell(zelle(content2, font, "white", "black"));
        tabelle.addCell(zelle(content3, font, "white", "black"));
        tabelle.addCell(zelle(content4, font, "white", "black"));
    }
    private static void setRow5(PdfPTable tabelle, String content1, String content2, String content3, String content4, String content5, Font font) {
        tabelle.addCell(zelle(content1, font, "white", "black"));
        tabelle.addCell(zelle(content2, font, "white", "black"));
        tabelle.addCell(zelle(content3, font, "white", "black"));
        tabelle.addCell(zelle(content4, font, "white", "black"));
        tabelle.addCell(zelle(content5, font, "white", "black"));
    }


    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        drucken1();
    }

    public static void drucken(Patient patient) throws DocumentException, FileNotFoundException {
        Document doc = new Document();
        File patientenakte = new File("Patientenakte.pdf");
        PdfWriter.getInstance(doc, new FileOutputStream(patientenakte));
        doc.open();
        patientendaten(doc, patient);
        einrichtungen(doc, patient);
        krankheitsgeschichte(doc, patient);
        doc.newPage();
        anamnese(doc, patient);

        doc.close();
    }

    public static void drucken1() throws DocumentException, FileNotFoundException {
        Document doc = new Document();

        File patientenakte = new File("Patientenakte.pdf");
        PdfWriter.getInstance(doc, new FileOutputStream(patientenakte));
        doc.open();
        Patientendaten1(doc);
        doc.close();
    }

    public static void Patientendaten1(Document doc) throws DocumentException {
        Chunk PatientendatenChunk = new Chunk("Metadaten");

        PdfPTable mutterTabelle = new PdfPTable(new float[]{1, 3});
        mutterTabelle.setWidthPercentage(100);

        PdfPTable Patientendatentabelle = erstelleTabelle(2);

        //Vorname

        Patientendatentabelle.addCell("Vorname");
        Patientendatentabelle.addCell("TEst");

        doc.add(Patientendatentabelle);
    }


    public static void patientendaten(Document doc, Patient patient) throws DocumentException {
        Anchor test = new Anchor("Patientendaten");

        Font contentFont = FontFactory.getFont("Arial", 16, BaseColor.BLACK);

        PdfPTable patientendatentabelle = erstelleTabelle(2);
        setRow2(patientendatentabelle, "Nachname", patient.nachname, contentFont);
        setRow2(patientendatentabelle, "Vorname", patient.vorname, contentFont);
        setRow2(patientendatentabelle, "Geschlecht", patient.geschlecht.toString(), contentFont);
        setRow2(patientendatentabelle, "Geburtstag", patient.geburtsdatum.toString(), contentFont);
        setRow2(patientendatentabelle, "Alter", "Keine Variable vorhanden", contentFont);
        setRow2(patientendatentabelle, "Einlieferung", patient.unterbringung.einlieferung, contentFont);
        setRow2(patientendatentabelle, "Entlassung", patient.unterbringung.entlassung, contentFont);
        setRow2(patientendatentabelle, "Strasse", patient.stamdaten.straße, contentFont);
        setRow2(patientendatentabelle, "Postleitzahl", String.valueOf(patient.stamdaten.plz), contentFont);
        setRow2(patientendatentabelle, "Ort", patient.stamdaten.ort, contentFont);
        setRow2(patientendatentabelle, "Land", patient.stamdaten.land, contentFont);
        setRow2(patientendatentabelle, "Telefonnummer", patient.stamdaten.telefon, contentFont);
        setRow2(patientendatentabelle, "Handynummer", patient.stamdaten.handy, contentFont);
        setRow2(patientendatentabelle, "E-Mail", patient.stamdaten.eMail, contentFont);
        setRow2(patientendatentabelle, "Kostenträger", patient.stamdaten.kostenträger, contentFont);
        setRow2(patientendatentabelle, "Versicherungsnummer", String.valueOf(patient.stamdaten.versicherungsnummer), contentFont);
        doc.add(patientendatentabelle);
    }

    public static void einrichtungen(Document doc, Patient patient) throws DocumentException {
        Anchor einrichtungen = new Anchor("Einrichtungen");
        Font contentFont = FontFactory.getFont("Arial", 16, BaseColor.BLACK);
        PdfPTable einrichtungenTabelle = erstelleTabelle(4);
        setRow4(einrichtungenTabelle, "Name", "Adresse", "Art des Arztes", "Telefonnummer", contentFont);
        for (int i = 0; i < 4; i++) {
            changeColor(einrichtungenTabelle.getRow(1).getCells()[i], "black", "hellblau");
        }
        for (int i = 0; i < patient.einrichtungen.size(); i++) {
            setRow4(einrichtungenTabelle, patient.einrichtungen.get(i).name, patient.einrichtungen.get(i).adresse, patient.einrichtungen.get(i).art, patient.einrichtungen.get(i).telefonnummer, contentFont);

            doc.add(einrichtungenTabelle);
        }
    }

    public static void anamnese(Document doc, Patient patient) throws DocumentException {
        Anchor anamnese = new Anchor("Anamnese");
        Font contentFont = FontFactory.getFont("Arial", 16, BaseColor.BLACK);

            PdfPTable anamneseTabelle = erstelleTabelle(2);
            setRow2(anamneseTabelle, "Groesse in cm", String.valueOf(patient.anamnese.groesse), contentFont);
            setRow2(anamneseTabelle, "Gewicht in kg", String.valueOf(patient.anamnese.gewicht), contentFont);
            if(patient.anamnese.behinderung == true){
                setRow2(anamneseTabelle, "Behinderung", "Ja", contentFont);
                setRow2(anamneseTabelle, "Grad d. Behinderung", String.valueOf(patient.anamnese.grad), contentFont );
            }else{
                setRow2(anamneseTabelle, "Behinderung", "Nein", contentFont);
                setRow2(anamneseTabelle, "Grad d. Behinderung", "-", contentFont);
            }
            setRow2(anamneseTabelle,"Endokrinologische Störungen", patient.anamnese.endokrinologisch.toString(), contentFont);
            setRow2(anamneseTabelle,"mit adipositas ass. Syndrome", patient.anamnese.adipositasSyndrome.toString(), contentFont);
            setRow2(anamneseTabelle, "Medikamenteninduzierte Adipositas", patient.anamnese.adipositasMedikamente.toString(), contentFont);
            setRow2(anamneseTabelle, "Weitere chron. Erkrankungen", patient.anamnese.chronischeKrankheiten.toString(), contentFont);
            doc.add(anamneseTabelle);
    }
    public static void krankheitsgeschichte(Document doc, Patient patient) throws DocumentException {
        Anchor krankheitsgesch = new Anchor("Krankheitsgeschichte");
        Font contentFont = FontFactory.getFont("Arial", 16, BaseColor.BLACK);
        PdfPTable krankheitsTabelle = erstelleTabelle(4);
        setRow5(krankheitsTabelle, "Datum", "Typ", "ICD-10", "Beschreibung","Arzt", contentFont);
        for (int i = 0; i < 5; i++) {
            changeColor(krankheitsTabelle.getRow(1).getCells()[i], "black", "hellblau");
        }
        for (int i = 0; i < patient.einrichtungen.size(); i++) {
            setRow5(krankheitsTabelle, "Keine passende Variable gefunden", patient.krankheits.get(i).type.toString(), patient.krankheits.get(i).icd10, patient.krankheits.get(i).beschreibung, patient.krankheits.get(i).arzt, contentFont);
        }
        doc.add(krankheitsTabelle);
    }
}
