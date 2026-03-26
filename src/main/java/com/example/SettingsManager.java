package com.example;

import java.nio.file.Paths;
import java.nio.file.Path;

public class SettingsManager {
    private static SettingsManager instance;
    private Settings settings;

    private SettingsManager() { 
        loadSettingsFromDisk(); 
    }

    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    private static void loadSettingsFromDisk() {
        //To-Do: написать функцию для загрузки настроек из json
    }

    private static void saveSettingsToDisk() {
        // To-Do: Написать функцию для сохранения настроек на диск
    }

    public static Path getConfigDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String appName = "INTRIGA-PDF-parser"; // Имя папки твоего приложения

        if (os.contains("win")) {
            // Логика для Windows
            String appData = System.getenv("LOCALAPPDATA");
            // Если LOCALAPPDATA по какой-то причине недоступна, падаем в домашнюю папку
            return Paths.get(appData != null ? appData : userHome, appName);
            
        } else if (os.contains("mac")) {
            // Логика для macOS
            return Paths.get(userHome, "Library", "Application Support", appName);
            
        } else {
            // Логика для Linux / Unix
            // Используем стандарт XDG, прячем папку в .config
            return Paths.get(userHome, ".config", appName.toLowerCase());
        }
    }

    // Метод для получения полного пути именно к файлу настроек
    public static Path getSettingsFilePath() {
        return getConfigDirectory().resolve("settings.json");
    }
}
