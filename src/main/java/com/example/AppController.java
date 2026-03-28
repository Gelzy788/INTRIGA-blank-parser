package com.example;

import com.deepoove.poi.data.PictureRenderData;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;;

public class AppController {
    // TODO: Убрать системные логи перед релизом
    public ProcessingReport compileFiles(List<File> filesList) {
        PdfParser parser = new PdfParser();
        BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
        ExcelManager excelManager = new ExcelManager();
        WordCreator wordCreator = new WordCreator();
        
        ProcessingReport reports = new ProcessingReport();
        ArrayList<Parcel> parcels = new ArrayList<>();
        
        // Инициализация таблицы excel
        excelManager.initSheet("blanks");

        // Проходимся по всем файлам из списка
        for (File blank : filesList) {
            System.out.println("--------------------");
            System.out.println("FILE " + blank.getPath());

            Parcel parcel = parser.pdfToParcel(blank);
            System.out.println(parcel);
            parcels.add(parcel);

            // Генерируем штрихкод
            try {
                PictureRenderData barcode = barcodeGenerator.generateParcelBarcode(parcel.getBarcodeNum());
                parcel.setBarcode(barcode);
            } catch (Exception err){
                reports.addError(blank.getName(), err.getMessage());
                continue;
            }

            
            excelManager.tableAdd(parcel); // Добавляем данные из бланка в таблицу
            reports.addSuccess(); // Добавляем в reports одну успешную обработку
        }

        // Сохраняем xlsx таблицу по пути из настроек
        excelManager.saveToFile(SettingsManager.getInstance().getExcelPath() + "/" + LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES) + ".xlsx");

        List<Map<String, Parcel>> pages = wordCreator.cutToPages(parcels); // Слздается список страниц, в котором каждый элемент - Map с данными для однйо страницы

        // Служебный вывод
        for (Map<String, Parcel> page : pages) {
            for (String key : page.keySet()) {
                System.out.println(key + ": " + page.get(key));
            }
        }

        // Сохранение docx документа
        try {
            wordCreator.createDocx(pages, SettingsManager.getInstance().getDocxPath() + "/" + LocalDateTime.now() + ".docx");
        } catch (Exception err) {
            err.printStackTrace();
        }

        return reports;
    }

    // public static void main(String[] args) {

    //     PdfParser parser = new PdfParser();
    //     BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
    //     ExcelManager excelManager = new ExcelManager();
    //     WordCreator wordCreator = new WordCreator();
        
    //     excelManager.initSheet("test");
    //     ArrayList<Parcel> parcels = new ArrayList<>();

    //     for (int i = 1; i < 15; i++) {
    //         System.out.println("--------------------");
    //         System.out.println("FILE " + i + ".pdf:");
    //         File pdfFile = new File("data/pdf/" + i + ".pdf"); //  Инициализация pdf файла

    //         Parcel parcel = parser.pdfToParcel(pdfFile);
    //         System.out.println(parcel);
    //         parcels.add(parcel);

    //         try {
    //             PictureRenderData barcode = barcodeGenerator.generateParcelBarcode(parcel.getBarcodeNum());
    //             parcel.setBarcode(barcode);
    //             // ImageIO.write(barcode, "png", barcodeImage);
    //         } catch (Exception err){
    //             err.printStackTrace();
    //         }

    //         excelManager.tableAdd(parcel);
    //     }
    //     String excelPath = "data/excel-tables/test.xlsx";
    //     excelManager.saveToFile(excelPath);

    //     List<Map<String, Parcel>> pages = wordCreator.cutToPages(parcels);

    //     for (Map<String, Parcel> page : pages) {
    //         for (String key : page.keySet()) {
    //             System.out.println(key + ": " + page.get(key));
    //         }
    //     }

    //     try {
    //         wordCreator.createDocx(pages);
    //     } catch (Exception err) {
    //         err.printStackTrace();
    //     }
        
    // }
}