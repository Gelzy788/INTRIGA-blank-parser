package com.example.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.net.URL;

public class MainController {

    @FXML private BorderPane contentArea;
    @FXML private Button btnQueue;
    @FXML private Button btnHistory;
    @FXML private Button btnSettings;

    @FXML
    public void initialize() {
        // При старте программы сразу загружаем окно Queue в центр
        loadPage("view_queue.fxml");

        // Привязываем кнопки к загрузке разных файлов
        btnQueue.setOnAction(e -> loadPage("view_queue.fxml"));
        
        btnHistory.setOnAction(e -> {
            System.out.println("Тут будет загружаться view_history.fxml");
            // loadPage("view_history.fxml");
        });
        
        btnSettings.setOnAction(e -> {loadPage("view_settings.fxml");});
    }

    private void loadPage(String fxmlFileName) {
        try {
            URL fileUrl = getClass().getResource("/frontend/" + fxmlFileName);
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML файл не найден: " + fxmlFileName);
            }
            Parent newScreen = FXMLLoader.load(fileUrl);
            contentArea.setCenter(newScreen); // Вставляем интерфейс в центр
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}