package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

public class InterfaceController {

    @FXML private Button uploadButton;

    @FXML private Button historyButton;

    @FXML private Button settingsButton;

    @FXML private Label fileCountLabel;

    @FXML private ListView<String> fileListView;

    @FXML private Button processButton;

    @FXML
    public void initialize() {
        
        // 1. Логика загрузки PDF файлов
        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите PDF документы");

            // Ограничение: только PDF
            FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(pdfFilter);

            Window window = uploadButton.getScene().getWindow();
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(window);

            if (selectedFiles != null) {
                for (File file : selectedFiles) {
                    String fileName = file.getName();
                    if (!fileListView.getItems().contains(fileName)) {
                        System.out.println("Загружен файл:" + fileName);
                        fileListView.getItems().add(fileName);
                    }
                }
                updateCounters();
            }
        });

        // 2. Кнопка History в боковом меню
        historyButton.setOnAction(event -> {
            System.out.println("Открываем окно или вкладку истории сессий...");
        });

        // 3. Кнопка Settings в боковом меню
        settingsButton.setOnAction(event -> {
            System.out.println("Открываем окно настроек...");
        });

        // 4. Кнопка отправки на обработку
        processButton.setOnAction(event -> {
            if (fileListView.getItems().isEmpty()) {
                System.out.println("Список пуст. Нечего обрабатывать.");
                return;
            }
            
            System.out.println("--- ЗАПУСК ОБРАБОТКИ ---");
            for (String path : fileListView.getItems()) {
                System.out.println("Готовим файл: " + path);
                // Здесь будет вызов бэкенда парсера
            }
        });
    }

    // Вспомогательный метод для обновления всех счетчиков в интерфейсе
    private void updateCounters() {
        int count = fileListView.getItems().size();
        
        // Обновляем текст в правой панели статистики
        fileCountLabel.setText(count + " / 2000");
        
        // Обновляем текст на красной кнопке внизу
        processButton.setText("ОТПРАВИТЬ НА ОБРАБОТКУ (" + count + ") >");
    }
}