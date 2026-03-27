package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        SettingsManager.getInstance(); // Создаем объект класса SettingsManager
        SettingsManager.getInstance().loadSettingsFromDisk(); // Загружаем настройки
        System.out.println("Система инициализирована, настройки загружены.");
    }

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Загружаем интерфейс
        java.net.URL resourceUrl = getClass().getResource("/frontend/main_layout.fxml");
        Parent root = FXMLLoader.load(resourceUrl); 
        
        // 2. Применяем темную тему к этому интерфейсу (если нужно)
        boolean isDark = SettingsManager.getInstance().getIsDarkMode();
        if (isDark) {
            String darkThemeUrl = getClass().getResource("/frontend/dark-theme.css").toExternalForm();
            root.getStylesheets().add(darkThemeUrl);
        }

        // 3. Создаем сцену из нашего готового (и перекрашенного) интерфейса
        Scene scene = new Scene(root);

        // 4. Настраиваем и показываем окно
        stage.setScene(scene);
        stage.setTitle("INTRIGA pdf parser");
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        // ЭТАП 3: Завершение (сохранение перед выходом)
        // Даже если пользователь не нажимал "Сохранить" в меню, 
        // мы гарантируем запись текущего состояния объекта Settings на диск.
        SettingsManager.getInstance().saveSettingsToDisk();
        System.out.println("Настройки сохранены. Работа завершена.");
    }
}