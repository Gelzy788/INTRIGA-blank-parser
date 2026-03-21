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

    public Parcel pdfToParcel(File file){
        if (file == null) {
            throw new IllegalArgumentException("Файл не загружен");
        }

        try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(file))) {
            PDPage page = document.getPage(0);
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            // Устанавливаем основные регионы
            PDFTextStripperByArea typeChecker = new PDFTextStripperByArea();
            typeChecker.addRegion("check_barcode", new Rectangle2D.Double(222, 76, 137, 13));
            typeChecker.extractRegions(page);
            String checkText = typeChecker.getTextForRegion("check_barcode").trim();

            boolean isTypeOne = checkText.length() > 1;

            // Проверка на тип документа
            PDFTextStripperByArea parser = new PDFTextStripperByArea();
            parser.setSortByPosition(true);

            if (isTypeOne) {
                // Тип 1. Находим нужные данные
                parser.addRegion("barcode", new Rectangle2D.Double(222, 76, 137, 13));
                parser.addRegion("receiver", new Rectangle2D.Double(211, 146, 210, 39));
                parser.addRegion("address", new Rectangle2D.Double(215, 186, 205, 55));
                parser.addRegion("index", new Rectangle2D.Double(216, 252, 95, 25));
            } else {
                // Тип 2. Находим нужные данные
                parser.addRegion("barcode", new Rectangle2D.Double(10, 273, 136, 19));
                parser.addRegion("receiver", new Rectangle2D.Double(216, 121, 189, 23));
                parser.addRegion("address", new Rectangle2D.Double(216, 145, 189, 41));
                parser.addRegion("index", new Rectangle2D.Double(327, 185, 78, 24));
            }

            parser.extractRegions(page);

            return new Parcel(
                    parser.getTextForRegion("barcode").trim().replace(" ", ""),
                    parser.getTextForRegion("index").trim(),    // Обратите внимание на порядок в вашем конструкторе!
                    parser.getTextForRegion("address").trim().replaceAll("\\R", " "),
                    parser.getTextForRegion("receiver").trim()
            );
        } catch (IOException err) {
            System.out.println("ОШИБКА ПРИ ОБРАБОТКЕ PDF ФАЙЛА:");
            err.printStackTrace();
            return null;
        }
    }

}
