package com.example.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import java.io.File;

import com.example.SettingsManager;

import javafx.scene.Parent;

public class SettingsController {

    @FXML private TextField excelPathField;
    @FXML private Button btnBrowseExcel;
    
    @FXML private TextField docxPathField;
    @FXML private Button btnBrowseDocx;
    
    @FXML private Button btnThemeLight;
    @FXML private Button btnThemeDark;
    

    // Переменная для хранения выбранной темы
    private boolean isDarkMode = SettingsManager.getInstance().getIsDarkMode();

    @FXML
    public void initialize() {
        // --- 0. ЗАГРУЗКА ТЕКУЩИХ ДАННЫХ В ИНТЕРФЕЙС ---
        excelPathField.setText(SettingsManager.getInstance().getExcelPath());
        docxPathField.setText(SettingsManager.getInstance().getDocxPath());
        
        // Сразу при открытии настроек перекрашиваем кнопки в правильный цвет
        updateThemeButtons(); 

        // --- 1. Логика выбора папок ---
        btnBrowseExcel.setOnAction(e -> chooseDirectory(excelPathField, "Выберите папку для Excel", "excel"));
        btnBrowseDocx.setOnAction(e -> chooseDirectory(docxPathField, "Выберите папку для Word", "word"));

        // --- 2. Логика переключения темы ---
        btnThemeLight.setOnAction(e -> {
            isDarkMode = false;
            SettingsManager.getInstance().setIsDarkMode(false);
            updateThemeButtons();
        });

        btnThemeDark.setOnAction(e -> {
            isDarkMode = true;
            SettingsManager.getInstance().setIsDarkMode(true);
            updateThemeButtons();
        });
    }

    // Вспомогательный метод для вызова окна выбора папки
    private void chooseDirectory(TextField targetField, String title, String pathType) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        
        // Пытаемся открыть окно в текущей папке, если она существует
        File currentDir = new File(targetField.getText());
        if (currentDir.exists() && currentDir.isDirectory()) {
            directoryChooser.setInitialDirectory(currentDir);
        }

        Window window = targetField.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(window);

        if (selectedDirectory != null) {
            targetField.setText(selectedDirectory.getAbsolutePath());
            if (pathType == "excel") {
                SettingsManager.getInstance().setExcelPath(selectedDirectory.getAbsolutePath());
            }
            else {
                SettingsManager.getInstance().setDocxPath(selectedDirectory.getAbsolutePath());
            }
            
        }
    }

    // Вспомогательный метод для перекраски кнопок темы
    private void updateThemeButtons() {
        // 1. Меняем визуальное состояние самих кнопок
        btnThemeLight.getStyleClass().removeAll("btn-toggle-active", "btn-toggle-inactive");
        btnThemeDark.getStyleClass().removeAll("btn-toggle-active", "btn-toggle-inactive");

        if (isDarkMode) {
            btnThemeDark.getStyleClass().add("btn-toggle-active");
            btnThemeLight.getStyleClass().add("btn-toggle-inactive");
        } else {
            btnThemeLight.getStyleClass().add("btn-toggle-active");
            btnThemeDark.getStyleClass().add("btn-toggle-inactive");
        }

        // 2. Применяем тему к КОРНЕВОМУ УЗЛУ приложения
        if (btnThemeLight.getScene() == null) return; 
        
        // Берем корневой элемент (наш main_layout), а не саму сцену
        Parent rootNode = btnThemeLight.getScene().getRoot(); 

        try {
            String darkThemeUrl = getClass().getResource("/frontend/dark-theme.css").toExternalForm();
            
            if (isDarkMode) {
                // Добавляем темную тему поверх светлой
                if (!rootNode.getStylesheets().contains(darkThemeUrl)) {
                    rootNode.getStylesheets().add(darkThemeUrl);
                }
            } else {
                // Удаляем темную тему
                rootNode.getStylesheets().remove(darkThemeUrl);
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка: файл dark-theme.css не найден в папке /frontend/");
        }
    }
}