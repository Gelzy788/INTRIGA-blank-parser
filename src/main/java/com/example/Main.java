package com.example;

import java.io.File;


public class Main {
    public static void main(String[] args) {
        for (int i = 1; i < 9; i++) {
            System.out.println("--------------------");
            System.out.println("FILE " + i + ".pdf:");
            File file = new File("data/pdf/" + i + ".pdf");
            PdfParser parcer = new PdfParser();

            Parcel parcel1 = parcer.pdfToParcel(file);

            System.out.println(parcel1);
        }
    }
}