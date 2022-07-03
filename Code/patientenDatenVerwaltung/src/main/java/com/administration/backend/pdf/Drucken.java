package com.administration.backend.pdf;

import com.administration.backend.Patient;

import com.administration.backend.pdf.util.PrintPdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Drucken {

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
        PrintPdf.print("Patientenakte.pdf");
        PrintPdf.deleteFile("Patientenakte.pdf");
    }

    public static void patientendaten(Document doc, Patient patient) throws DocumentException {
        Font headerFont = FontFactory.getFont("Arial", 16, Font.BOLD);
        Paragraph ueberschrift = new Paragraph("Patientendaten", headerFont);
        ueberschrift.setAlignment(Element.ALIGN_CENTER);
        ueberschrift.setSpacingAfter(15);
        doc.add(ueberschrift);

        Font contentFont = FontFactory.getFont("Arial", 12, BaseColor.BLACK);

        PdfPTable patientendatentabelle = new PdfPTable(new float[]{1,2});
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
        for (int i = 0; i < 16; i++) {
            changeColor(patientendatentabelle.getRow(i).getCells()[0], "black", "hellblau");
        }
        doc.add(patientendatentabelle);
    }


    public static void einrichtungen(Document doc, Patient patient) throws DocumentException {
        Font headerFont = FontFactory.getFont("Arial", 16, Font.BOLD);
        Paragraph ueberschrift = new Paragraph("Einrichtungen", headerFont);
        ueberschrift.setAlignment(Element.ALIGN_CENTER);
        ueberschrift.setSpacingBefore(20);
        ueberschrift.setSpacingAfter(10);
        doc.add(ueberschrift);



        Font contentFont = FontFactory.getFont("Arial", 12, BaseColor.BLACK);
        PdfPTable einrichtungenTabelle = new PdfPTable(4);
        setRow4(einrichtungenTabelle, "Name", "Adresse", "Art des Arztes", "Telefonnummer", contentFont);
        for (int i = 0; i < 4; i++) {
            changeColor(einrichtungenTabelle.getRow(0).getCells()[i], "black", "hellblau");
        }
        for (int i = 0; i < patient.einrichtungen.size(); i++) {
            setRow4(einrichtungenTabelle, patient.einrichtungen.get(i).name, patient.einrichtungen.get(i).adresse, patient.einrichtungen.get(i).art, patient.einrichtungen.get(i).telefonnummer, contentFont);

            doc.add(einrichtungenTabelle);
        }
    }

    public static void anamnese(Document doc, Patient patient) throws DocumentException {
        Font headerFont = FontFactory.getFont("Arial", 16, Font.BOLD);
        Paragraph ueberschrift = new Paragraph("Anamnese", headerFont);
        ueberschrift.setAlignment(Element.ALIGN_CENTER);
        ueberschrift.setSpacingBefore(20);
        ueberschrift.setSpacingAfter(10);
        doc.add(ueberschrift);



        Font contentFont = FontFactory.getFont("Arial", 12, BaseColor.BLACK);

            PdfPTable anamneseTabelle = new PdfPTable(2);
            setRow2(anamneseTabelle, "Groesse in cm", String.valueOf(patient.anamnese.groesse), contentFont);
            setRow2(anamneseTabelle, "Gewicht in kg", String.valueOf(patient.anamnese.gewicht), contentFont);
            if(patient.anamnese.behinderung == true){
                setRow2(anamneseTabelle, "Behinderung", "Ja", contentFont);
                setRow2(anamneseTabelle, "Grad der Behinderung", String.valueOf(patient.anamnese.grad), contentFont );
            }else{
                setRow2(anamneseTabelle, "Behinderung", "Nein", contentFont);
                setRow2(anamneseTabelle, "Grad der Behinderung", "-", contentFont);
            }
            setRow2(anamneseTabelle,"Endokrinologische Störungen", patient.anamnese.endokrinologisch.toString(), contentFont);
            setRow2(anamneseTabelle,"mit adipositas assoziierte Syndrome", patient.anamnese.adipositasSyndrome.toString(), contentFont);
            setRow2(anamneseTabelle, "Medikamenteninduzierte Adipositas", patient.anamnese.adipositasMedikamente.toString(), contentFont);
            setRow2(anamneseTabelle, "Weitere chronische Erkrankungen", patient.anamnese.chronischeKrankheiten.toString(), contentFont);
        for (int i = 0; i < 8; i++) {
            changeColor(anamneseTabelle.getRow(i).getCells()[0], "black", "hellblau");
        }
            doc.add(anamneseTabelle);
    }
    public static void krankheitsgeschichte(Document doc, Patient patient) throws DocumentException {
        Font headerFont = FontFactory.getFont("Arial", 16, Font.BOLD);
        Paragraph ueberschrift = new Paragraph("Krankheitsgeschichte", headerFont);
        ueberschrift.setAlignment(Element.ALIGN_CENTER);
        ueberschrift.setSpacingBefore(20);
        ueberschrift.setSpacingAfter(10);
        doc.add(ueberschrift);

        Font contentFont = FontFactory.getFont("Arial", 12, BaseColor.BLACK);
        PdfPTable krankheitsTabelle = new PdfPTable(new float[]{2, 1, 1, 4, 2});
        setRow5(krankheitsTabelle, "Datum", "Typ", "ICD-10", "Beschreibung","Arzt", contentFont);
        for (int i = 0; i < 5; i++) {
            changeColor(krankheitsTabelle.getRow(0).getCells()[i], "black", "hellblau");
        }
        for (int i = 0; i < patient.einrichtungen.size(); i++) {
            setRow5(krankheitsTabelle, "Keine passende Variable gefunden", patient.krankheits.get(i).type.toString(), patient.krankheits.get(i).icd10, patient.krankheits.get(i).beschreibung, patient.krankheits.get(i).arzt, contentFont);
        }
        doc.add(krankheitsTabelle);
    }
}
