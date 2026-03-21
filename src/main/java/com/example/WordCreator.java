package com.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

public class WordCreator {
    public void putData(String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("barcode", Pictures.ofLocal("data/barcodes/1.png").size(181, 39).create());
        try {
            XWPFTemplate.compile("src/main/resources/test.docx").render(data).writeToFile("finishTest.docx");
        } catch (IOException err) {
            err.printStackTrace();
        }
        
    }

    public List<Map<String, Parcel>> cutToPages(List<Parcel> allParcels) {
        List<Map<String, Parcel>> pages = new ArrayList<>();
        
        // Шагаем по массиву по 8 элементов
        for (int i = 0; i < allParcels.size(); i += 8) {
            
            // Внутренний массив заменяем на Map (Словарь). 
            // Он выполняет ту же роль, но позволяет задать имена (label1, label2)
            Map<String, Parcel> page = new HashMap<>();
            
            for (int j = 0; j < 8; j++) {
                int currentIndex = i + j;
                String wordKey = "label" + (j + 1); // Генерируем "label1", "label2"...
                
                // Если элемент существует в allParcels — берем его
                if (currentIndex < allParcels.size()) {
                    page.put(wordKey, allParcels.get(currentIndex));
                } else {
                    // Если исходник закончился — добиваем страницу null-ами!
                    // Исходный массив allParcels при этом не страдает.
                    page.put(wordKey, null);
                }
            }
            pages.add(page);
        }
        return pages;
    }

    // Генерация docx файла
    public void createDocx(List<Map<String, Parcel>> pages) throws IOException {
        // Кладем список в словарь, чтобы библиотека поняла, к какому тегу он принадлежит
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("pages", pages);

        // Компилируем файл по шалону
        XWPFTemplate.compile("src/main/resources/template.docx")
                            .render(templateData)
                            .writeToFile("test.docx");
    }
}
