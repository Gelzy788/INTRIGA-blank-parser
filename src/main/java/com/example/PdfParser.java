package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import java.awt.geom.Rectangle2D;
import org.apache.pdfbox.pdmodel.PDPage;
import java.io.File;
import java.io.IOException;

public class PdfParser {
    private static final double REGION_X = 205.0;
    private static final double REGION_Y = 0.0;
    private static final double REGION_WIDTH = 251.0;
    private static final double REGION_HEIGHT = 319.0;

    public String[] parseAll(File file){
        if (file == null) {
            throw new IllegalArgumentException("Файл не загружен");
        }

        try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(file))) {
            PDPage page = document.getPage(0);
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            Rectangle2D dataRegion = new Rectangle2D.Double(REGION_X, REGION_Y, REGION_WIDTH, REGION_HEIGHT);
            stripper.addRegion("data", dataRegion);
            stripper.extractRegions(page);
            
            String[] splitData = stripper.getTextForRegion("data").trim().split("\\R");
            return splitData;
        } catch (IOException err) {
            System.out.println("ОШИБКА ПРИ ОБРАБОТКЕ PDF ФАЙЛА:");
            err.printStackTrace();
            return new String[0];
        }
    }

    // public String[] splitDataToNormal(String[] data) {
    //     if (data.length == 7)
    // }

    public Parcel createParcel(File file) {
        return null;
    }
}
