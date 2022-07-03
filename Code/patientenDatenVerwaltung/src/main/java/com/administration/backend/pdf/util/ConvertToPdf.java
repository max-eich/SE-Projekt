package com.administration.backend.pdf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;

public class ConvertToPdf {
    public static void convert() {

        File inputWord = new File("src/main/resources/com/administration/pdf/Temp-Patientenakte.docx");
        File outputFile = new File("src/main/resources/com/administration/pdf/Patientenakte.pdf");
        try {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = com.documents4j.job.LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            outputStream.close();
            System.out.println("pdf build: success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}