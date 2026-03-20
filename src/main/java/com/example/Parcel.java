package com.example;

public class Parcel {
    private int barcodeNum;
    private int index;
    private String adress;
    private String receiver;

    public Parcel(int barcodeNum, int index, String adress, String receiver) {
        if (adress == null || receiver == null) {
            throw new IllegalArgumentException("Все переменные должны быть заполнены");
        }
        
        this.barcodeNum = barcodeNum;
        this.index = index;
        this.adress = adress;
        this.receiver = receiver;
    }

    
}
