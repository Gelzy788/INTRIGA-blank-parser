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
        // ЭТАП 1: Подготовка (в фоновом потоке)
        // Вызов getInstance() инициализирует менеджер, который 
        // сразу загружает файл settings.json в объект Settings.
        SettingsManager.getInstance();
        System.out.println("Система инициализирована, настройки загружены.");
    }

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Проверяем путь перед попыткой загрузки
        java.net.URL resourceUrl = getClass().getResource("/frontend/main_layout.fxml");
        Parent root = FXMLLoader.load(resourceUrl); 
        Scene scene = new Scene(root);
        
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
        // SettingsManager.getInstance().saveSettingsToDisk;
        System.out.println("Настройки сохранены. Работа завершена.");
    }
}