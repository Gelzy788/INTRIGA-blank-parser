package com.example;

import com.deepoove.poi.data.PictureRenderData;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        PdfParser parcer = new PdfParser();
        BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
        ExcelManager excelManager = new ExcelManager();
        WordCreator wordCreator = new WordCreator();
        
        excelManager.initSheet("test");
        ArrayList<Parcel> parcels = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            System.out.println("--------------------");
            System.out.println("FILE " + i + ".pdf:");
            File pdfFile = new File("data/pdf/" + i + ".pdf"); //  Инициализация pdf файла

            Parcel parcel = parcer.pdfToParcel(pdfFile);
            System.out.println(parcel);
            parcels.add(parcel);

            try {
                PictureRenderData barcode = barcodeGenerator.generateParcelBarcode(parcel.getBarcodeNum());
                parcel.setBarcode(barcode);
                // ImageIO.write(barcode, "png", barcodeImage);
            } catch (Exception err){
                err.printStackTrace();
            }

            excelManager.tableAdd(parcel);
        }
        String excelPath = "data/excel-tables/test.xlsx";
        excelManager.saveToFile(excelPath);

        List<Map<String, Parcel>> pages = wordCreator.cutToPages(parcels);

        for (Map<String, Parcel> page : pages) {
            for (String key : page.keySet()) {
                System.out.println(key + ": " + page.get(key));
            }
        }

        try {
            wordCreator.createDocx(pages);
        } catch (Exception err) {
            err.printStackTrace();
        }
        
    }
}