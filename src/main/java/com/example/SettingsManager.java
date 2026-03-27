package com.example;

import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

public class SettingsManager {
    private static SettingsManager instance;
    private Boolean isDarkMode;
    private String excelPath;
    private String docxPath;

    private SettingsManager() { 
        loadSettingsFromDisk(); 
    }

    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    public void loadSettingsFromDisk() {
        //To-Do: написать функцию для загрузки настроек из json
        try {
            String srtingSettings = Files.readString(getSettingsPath());
            JSONObject jsonSettings = new JSONObject(srtingSettings);
            
            this.docxPath = jsonSettings.getString("docxPath");
            this.excelPath = jsonSettings.getString("excelPath");
            this.isDarkMode = jsonSettings.getBoolean("isDarkMode");
            
        } catch (Exception err) {
            err.printStackTrace();
        }
        
    }

    public void saveSettingsToDisk() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("excelPath", this.getExcelPath());
        jsonObject.put("docxPath", this.getDocxPath());
        jsonObject.put("isDarkMode", this.getIsDarkMode());

        try (FileWriter file = new FileWriter(getSettingsPath().toString())){
            file.write(jsonObject.toString(4));
            System.out.println("Сохранение прошло успешно!");
        }
        catch (Exception err) {
            System.out.println("Произошла ошибка сохранения :" + err);
        }
    }

    // To-Do: Сделать генерацию дефолтных настроек в json файле
    public static Path getSettingsPath() throws IOException{
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String appName = "INTRIGA-PDF-parser"; // Имя папки твоего приложения
        Path path = Paths.get("");

        if (os.contains("win")) {
            // Логика для Windows
            String appData = System.getenv("LOCALAPPDATA");
            // Если LOCALAPPDATA по какой-то причине недоступна, падаем в домашнюю папку
            path = Paths.get(appData != null ? appData : userHome, appName);
            
        } else if (os.contains("mac")) {
            // Логика для macOS
            path = Paths.get(userHome, "Library", "Application Support", appName);
            
        } else {
            // Логика для Linux / Unix
            // Используем стандарт XDG, прячем папку в .config
            path = Paths.get(userHome, ".config", appName.toLowerCase());
        }

        Path settingsFile = path.resolve("settings.json");

        // Создаем директорию и файл, если папки или файла еще нет
        if (Files.notExists(path) || Files.notExists(settingsFile)) {
            try {
                // createDirectories безопаснее, так как создает все промежуточные папки, если их нет
                Files.createDirectories(path); 
                
                // Вычисляем путь к папке Загрузки текущего пользователя
                String downloadsPath = Paths.get(userHome, "Downloads").toString();
                
                // Формируем JSON с настройками по умолчанию
                JSONObject defaultSettings = new JSONObject();
                defaultSettings.put("isDarkMode", false);
                defaultSettings.put("excelPath", downloadsPath);
                defaultSettings.put("docxPath", downloadsPath);
                
                // Записываем сформированный JSON прямо в файл (файл создастся автоматически)
                Files.writeString(settingsFile, defaultSettings.toString(4));
                
                System.out.println("Папка и базовый файл настроек успешно созданы!");
            } catch (IOException e) {
                System.err.println("Не удалось создать папку или файл: " + e.getMessage());
            }
        } else {
            System.out.println("Файл настроек уже на месте, используем его.");
        }

        return settingsFile;
    }

    public Boolean getIsDarkMode(){
        return this.isDarkMode;
    }

    public String getExcelPath(){
        return this.excelPath;
    }

    public String getDocxPath(){
        return this.docxPath;
    }

    public void setIsDarkMode(Boolean isdarkMode){
        this.isDarkMode = isdarkMode;
    }

    public void setExcelPath(String excelPath){
        this.excelPath = excelPath;
    }

    public void setDocxPath(String docxPath){
        this.docxPath = docxPath;
    }
}
