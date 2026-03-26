package com.example.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.util.List;

public class QueueController {

    @FXML private Button uploadButton;
    @FXML private Label fileCountLabel;
    @FXML private ListView<String> fileListView;
    @FXML private Button processButton;

    @FXML
    public void initialize() {
        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите PDF документы");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));

            Window window = uploadButton.getScene().getWindow();
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(window);

            if (selectedFiles != null) {
                for (File file : selectedFiles) {
                    String fileName = file.getName();
                    if (!fileListView.getItems().contains(fileName)) {
                        fileListView.getItems().add(fileName);
                    }
                }
                updateCounters();
            }
        });

        processButton.setOnAction(event -> {
            if (fileListView.getItems().isEmpty()) {
                System.out.println("Список пуст.");
                return;
            }
            System.out.println("--- ЗАПУСК ОБРАБОТКИ ---");
            for (String path : fileListView.getItems()) {
                System.out.println("Готовим файл: " + path);
            }
        });
    }

    private void updateCounters() {
        int count = fileListView.getItems().size();
        fileCountLabel.setText(count + " / 2000");
        processButton.setText("ОТПРАВИТЬ НА ОБРАБОТКУ (" + count + ") >");
    }
}