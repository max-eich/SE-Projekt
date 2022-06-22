package de.rac.pdf.util;

import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class PrintPdf {

    public static void print(String path) {
        try {
            PDDocument document = PDDocument.load(new File(path));
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.print();
        } catch (PrinterAbortException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.print(e);
        }

    }
    public static void deleteFile(String path) {
        File myObj = new File(path);
        myObj.deleteOnExit();
    }

}


