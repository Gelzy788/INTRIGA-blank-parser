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
    public void start(Stage stage) throws Exception {
        // 1. Проверяем путь перед попыткой загрузки
        java.net.URL resourceUrl = getClass().getResource("/frontend/test2.fxml");
        
        if (resourceUrl == null) {
            System.err.println("❌ ОШИБКА: Файл test.fxml не найден!");
            System.err.println("Убедись, что после компиляции файл физически скопирован средой разработки в папку target/classes/frontend/ (или bin/frontend/)");
            return; // Останавливаем запуск окна, чтобы избежать NullPointerException
        }
        
        System.out.println("✅ Файл успешно найден по пути: " + resourceUrl);

        // 2. Если файл найден, безопасно загружаем интерфейс
        Parent root = FXMLLoader.load(resourceUrl); 
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("JavaFX Test");
        stage.setWidth(600);
        stage.setHeight(500);
        stage.show();
    }
}