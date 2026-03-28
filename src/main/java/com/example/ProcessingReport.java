package com.example;
import java.util.Map;
import java.util.HashMap;

public class ProcessingReport {
    private int successCount = 0;
    private Map<String, String> errors = new HashMap<>();

    public void addSuccess() {
        this.successCount++;
    }

    public void addError(String fileName, String error) {
        this.errors.put(fileName, error);
    }

    public int getSuccessCount() {
        return successCount;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public int getErrorsCount() {
        return errors.size();
    }

    // Проверка, все ли прошло идеально
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
