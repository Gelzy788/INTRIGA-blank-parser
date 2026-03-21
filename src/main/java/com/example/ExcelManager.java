package com.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font; // Убрал лишнюю ;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelManager {
    private Workbook book;
    private Sheet currentSheet; // Храним лист внутри менеджера

    public ExcelManager() {
        this.book = new HSSFWorkbook();
    }

    // Теперь метод просто настраивает лист внутри себя
    public void initSheet(String name) {
        this.currentSheet = this.book.createSheet(name);
        Row headerRow = currentSheet.createRow(0);

        String[] columns = {"Штрихкод", "Кому", "Куда", "Индекс"};

        CellStyle headerStyle = book.createCellStyle();
        Font font = book.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    // Больше не нужно передавать Sheet снаружи
    public void tableAdd(Parcel parcel) {
        if (this.currentSheet == null) {
            throw new IllegalStateException("Лист не инициализирован! Вызовите initSheet() перед добавлением данных.");
        }

        Row newRow = currentSheet.createRow(currentSheet.getLastRowNum() + 1);
        
        // Наполняем строку данными
        newRow.createCell(0).setCellValue(parcel.getBarcodeNum());
        newRow.createCell(1).setCellValue(parcel.getReceiver());
        newRow.createCell(2).setCellValue(parcel.getAddress());
        newRow.createCell(3).setCellValue(parcel.getIndex());
    }

    private void fitColumns() {
        for (int i = 0; i < 4; i++) {
            this.currentSheet.autoSizeColumn(i);
        }
    }

    // Сохранение результатов на диск
    public void saveToFile(String filePath) {
        // Расширяем столбцы перед сохранением
        fitColumns();

        // Сохраняем файл
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            book.write(out); // Физически записываем файл
            System.out.println("Файл успешно сохранен: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        } finally {
            try {
                book.close(); // Закрываем книгу для очистки памяти
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}