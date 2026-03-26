package com.example;

public class Settings {
    private Boolean isDarkMode;
    private String excelPath;
    private String docxPath;

    public Boolean getIsDarkMode(){
        return this.isDarkMode;
    }

    public String getExcelPath(){
        return this.excelPath;
    }

    public String getdocxPath(){
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
