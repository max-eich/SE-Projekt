package de.max;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import de.max.exception.ConvertException;

import java.io.*;

public class Convert {

    public static void convert(String input, String output) throws ConvertException {
        File inputWord = new File(input);
        File outputFile = new File(output);
        try {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.make();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            outputStream.close();
        } catch (Exception e) {
            throw new ConvertException("Failed to convert PDF");
        }
    }
}
