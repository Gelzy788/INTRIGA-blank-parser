package com.example.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML private BorderPane contentArea;
    @FXML private Button btnQueue;
    @FXML private Button btnSettings;

    // Кэш для хранения уже загруженных экранов
    private Map<String, Parent> viewsCache = new HashMap<>();

    @FXML
    public void initialize() {
        // При старте программы сразу загружаем окно Queue в центр
        loadPage("view_queue.fxml");

        // Привязываем кнопки к загрузке разных файлов
        btnQueue.setOnAction(e -> loadPage("view_queue.fxml"));
        btnSettings.setOnAction(e -> loadPage("view_settings.fxml"));
    }

    private void loadPage(String fxmlFileName) {
        try {
            // 1. Проверяем, есть ли уже этот экран в нашем кэше
            if (!viewsCache.containsKey(fxmlFileName)) {
                
                // Если нет - загружаем его из файла в первый раз
                URL fileUrl = getClass().getResource("/frontend/" + fxmlFileName);
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML файл не найден: " + fxmlFileName);
                }
                Parent newScreen = FXMLLoader.load(fileUrl);
                
                // Сохраняем загруженный экран в кэш
                viewsCache.put(fxmlFileName, newScreen);
            }

            // 2. Берем готовый экран из кэша и вставляем в центр
            contentArea.setCenter(viewsCache.get(fxmlFileName));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}