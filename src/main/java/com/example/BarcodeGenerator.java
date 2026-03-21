package com.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.PictureType;


public class BarcodeGenerator {
    static int BARCODE_WIDTH = 260;
    static int BARCODE_HEIGHT = 50;

    public PictureRenderData generateParcelBarcode(String barcodeText) throws WriterException{
        if (barcodeText == null || barcodeText.isEmpty()) {
            throw new IllegalArgumentException("Код штрихкода не должен быть пустым");
        }

        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.ITF, BARCODE_WIDTH, BARCODE_HEIGHT);

        // return MatrixToImageWriter.toBufferedImage(bitMatrix);
        return Pictures.ofBufferedImage(MatrixToImageWriter.toBufferedImage(bitMatrix), PictureType.PNG).size(BARCODE_WIDTH, BARCODE_HEIGHT).create();
    }
}
