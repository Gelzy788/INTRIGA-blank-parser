package com.example;

public class Parcel {
    private String barcodeNum;
    private String index;
    private String address;
    private String receiver;

    public Parcel(String barcodeNum, String index, String address, String receiver) {
        if (address == null || receiver == null) {
            throw new IllegalArgumentException("Все переменные должны быть заполнены");
        }
        
        this.barcodeNum = barcodeNum;
        this.index = index;
        this.address = address;
        this.receiver = receiver;
    }

    // Геттеры
    public String getBarcodeNum() {
        return barcodeNum;
    }

    public String getIndex() {
        return index;
    }

    public String getAddress() {
        return address;
    }

    public String getReceiver() {
        return receiver;
    }

    // Сеттеры
    public void setBarcodeNum(String barcodeNum) {
        this.barcodeNum = barcodeNum;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Адрес не может быть null");
        }
        this.address = address;
    }

    public void setReceiver(String receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("Получатель не может быть null");
        }
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return String.format("Parcel[barcode='%s',\n index='%s',\n address='%s',\n receiver='%s']", barcodeNum, index, address, receiver);
    }
}