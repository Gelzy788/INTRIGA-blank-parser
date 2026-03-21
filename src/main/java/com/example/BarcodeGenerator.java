package com.example;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


public class BarcodeGenerator {
    static int BARCODE_WIDTH = 181;
    static int BARCODE_HEIGHT = 39;

    public BufferedImage generateParcelBarcode(String barcodeText) throws WriterException{
        if (barcodeText == null || barcodeText.isEmpty()) {
            throw new IllegalArgumentException("Код штрихкода не должен быть пустым");
        }

        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.ITF, BARCODE_WIDTH, BARCODE_HEIGHT);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
