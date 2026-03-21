package com.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
}
