package com.example;

import com.deepoove.poi.data.PictureRenderData;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public class AppController {
    public ProcessingReport compileFiles(List<File> filesList) {
        PdfParser parser = new PdfParser();
        BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
        ExcelManager excelManager = new ExcelManager();
        WordCreator wordCreator = new WordCreator();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm");
        
        ProcessingReport reports = new ProcessingReport();
        ArrayList<Parcel> parcels = new ArrayList<>();
        
        // Инициализация таблицы excel
        excelManager.initSheet("blanks");

        // Переворачиваем список
        Collections.reverse(filesList);

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

        String timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).format(formatter).toString();

        // Сохраняем xlsx таблицу по пути из настроек
        excelManager.saveToFile(SettingsManager.getInstance().getExcelPath() + "/" + timeNow + ".xlsx");

        List<Map<String, Parcel>> pages = wordCreator.cutToPages(parcels); // Слздается список страниц, в котором каждый элемент - Map с данными для однйо страницы

        // Служебный вывод
        for (Map<String, Parcel> page : pages) {
            for (String key : page.keySet()) {
                System.out.println(key + ": " + page.get(key));
            }
        }

        // Сохранение docx документа
        try {
            wordCreator.createDocx(pages, SettingsManager.getInstance().getDocxPath() + "/" + timeNow + ".docx");
        } catch (Exception err) {
            err.printStackTrace();
        }

        return reports;
    }
}