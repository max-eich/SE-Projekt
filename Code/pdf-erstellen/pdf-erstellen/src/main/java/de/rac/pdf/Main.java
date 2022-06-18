package de.rac.pdf;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        String fileName = "C:\\Users\\Max\\Documents\\GitHub\\SE-Projekt\\Code\\pdf-erstellen\\pdf-erstellen\\test.docx";

        try (XWPFDocument doc = new XWPFDocument()) {

            // create a paragraph
            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.CENTER);

            // set font
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setItalic(true);
            r1.setFontSize(22);
            r1.setFontFamily("Nigger");
            r1.setText("I am first paragraph.");

            XWPFTable tabelle = doc.createTable(2, 2);
            tabelle.setTableAlignment(TableRowAlign.CENTER);

            XWPFParagraph paragraph = tabelle.getRow(0).getCell(0).getParagraphs().get(0);
            XmlCursor cursor = paragraph.getCTP().newCursor();

            XWPFTable innereTabelle = tabelle.getRow(0).getCell(0).insertNewTbl(cursor);

            var row = innereTabelle.createRow();

            var cell = row.addNewTableCell();

            XWPFParagraph paragraph1 = doc.createParagraph();
            XWPFRun r2 = paragraph1.createRun();
            r2.setBold(true);
            r2.setItalic(true);
            r2.setFontSize(22);
            r2.setFontFamily("Arial");
            r2.setText("Test");

            cell.setParagraph(paragraph1);

            var row2 = innereTabelle.createRow();

            var cell2 = row.addNewTableCell();

            XWPFParagraph paragraph2 = doc.createParagraph();
            XWPFRun r3 = paragraph2.createRun();
            r3.setBold(true);
            r3.setItalic(true);
            r3.setFontSize(22);
            r3.setFontFamily("Arial");
            r3.setText("Test");

            cell.setParagraph(paragraph1);



            //innereTabelle.getRow(0).getCell(0).setText("Pipippopo");
            //innereTabelle.getRow(0).getCell(1).setText("Kaka");

            // save it to .docx file
            try (FileOutputStream out = new FileOutputStream(fileName)) {
                doc.write(out);
            }

        }


    }
}
