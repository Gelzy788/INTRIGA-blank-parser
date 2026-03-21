package com.example;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Main {
    public static void main(String[] args) {
        // PdfParser parcer = new PdfParser();
        // BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
        // ExcelManager excelManager = new ExcelManager();
        
        // excelManager.initSheet("test");
        // for (int i = 1; i < 9; i++) {
        //     System.out.println("--------------------");
        //     System.out.println("FILE " + i + ".pdf:");
        //     File pdfFile = new File("data/pdf/" + i + ".pdf"); //  Инициализация pdf файла
        //     File barcodeImage = new File("data/barcodes/" + i + ".png");

        //     Parcel parcel = parcer.pdfToParcel(pdfFile);
        //     System.out.println(parcel);

        //     try {
        //         BufferedImage barcode = barcodeGenerator.generateParcelBarcode(parcel.getBarcodeNum());
        //         ImageIO.write(barcode, "png", barcodeImage);
        //     } catch (Exception err){
        //         err.printStackTrace();
        //     }

        //     excelManager.tableAdd(parcel);
        // }
        // String excelPath = "data/excel-tables/test.xlsx";
        // excelManager.saveToFile(excelPath);

        WordCreator wordCreator = new WordCreator();
        wordCreator.putData("АЛИ Негм");
    }
}