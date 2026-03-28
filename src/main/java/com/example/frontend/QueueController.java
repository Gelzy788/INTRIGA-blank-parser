package com.example.frontend;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javafx.scene.layout.Region;
import java.awt.Desktop;

import com.example.AppController;
import com.example.SettingsManager;

public class QueueController {

    @FXML private Button uploadButton;
    @FXML private Label fileCountLabel;
    @FXML private ListView<String> fileListView;
    @FXML private Button processButton;
    @FXML private Button removeFileButton;
    @FXML private Button clearListButton;

    private List<File> fileList = new ArrayList<>();

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
                        fileList.add(file);
                    }
                }
                updateCounters();
            }
        });

        removeFileButton.setOnAction(e -> {
            int selectedIndex = fileListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                fileListView.getItems().remove(selectedIndex);
                fileList.remove(selectedIndex);
                updateCounters();
            }
        });

        clearListButton.setOnAction(e -> {
            fileListView.getItems().clear();
            fileList.clear();
            updateCounters();
        });

        processButton.setOnAction(event -> {
            if (fileListView.getItems().isEmpty()) {
                System.out.println("Список пуст.");
                return;
            }
            System.out.println("--- ЗАПУСК ОБРАБОТКИ ---");
            // меняем текст кнопки на "ОБРАБОТКА..." и блокируем ее для нажатий
            processButton.setText("ОБРАБОТКА...");
            processButton.setDisable(true);

            // Асинхронный запуск обработки
            CompletableFuture.supplyAsync(() -> {
                AppController controller = new AppController();
                return controller.compileFiles(fileList);
            }).thenAccept(report -> {
                Platform.runLater(() -> {
                    processButton.setText("ОТПРАВИТЬ НА ОБРАБОТКУ");
                    processButton.setDisable(false);

                    showCompletionDialog(report.getSuccessCount(), report.getErrorsCount());
                });
            });
        });
    }

    private void updateCounters() {
        int count = fileListView.getItems().size();
        fileCountLabel.setText(count + " / 2000");
        processButton.setText("ОТПРАВИТЬ НА ОБРАБОТКУ (" + count + ") >");
    }

    private void showCompletionDialog(int successCount, int errorCount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Отчет системы");
        alert.setHeaderText("Обработка файлов успешно завершена!");
        
        // Формируем текст отчета
        String content = "Успешно обработано: " + successCount + " файлов.\n";
        if (errorCount > 0) {
            content += "Ошибок при обработке: " + errorCount + " файлов.";
        }
        alert.setContentText(content);

        // 1. Создаем свои кастомные кнопки
        ButtonType btnOpenExcel = new ButtonType("Открыть Excel");
        ButtonType btnOpenWord = new ButtonType("Открыть Word");
        ButtonType btnClose = new ButtonType("Закрыть", ButtonType.CANCEL.getButtonData());

        // Заменяем стандартную кнопку "ОК" на наши
        alert.getButtonTypes().setAll(btnOpenExcel, btnOpenWord, btnClose);

        // 2. Применяем дизайн (чтобы окно не было скучно-белым)
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/frontend/style.css").toExternalForm());
        if (SettingsManager.getInstance().getIsDarkMode()) {
            dialogPane.getStylesheets().add(getClass().getResource("/frontend/dark-theme.css").toExternalForm());
        }
        dialogPane.setMinHeight(Region.USE_PREF_SIZE); // Чтобы текст не обрезался

        // 3. Показываем окно и ждем, какую кнопку нажмет пользователь
        alert.showAndWait().ifPresent(buttonPressed -> {
            if (buttonPressed == btnOpenExcel) {
                openDirectory(SettingsManager.getInstance().getExcelPath());
            } else if (buttonPressed == btnOpenWord) {
                openDirectory(SettingsManager.getInstance().getDocxPath());
            }
        });
    }

    private void openDirectory(String path) {
    try {
        File target = new File(path);
        
        // Проверяем, что папка действительно существует по этому пути
        if (target.exists() && target.isDirectory()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(target);
            } else {
                System.err.println("Класс Desktop не поддерживается на этой ОС.");
            }
        } else {
            System.err.println("Папка не найдена или была удалена: " + path);
            // Тут можно вывести Alert, что папка не существует
        }
    } catch (IOException e) {
        System.err.println("Ошибка при открытии папки: " + e.getMessage());
    }
}
}