module com.example.blankparser {
    // 1. JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // 2. Стандартные модули Java (которые не входят в базовый набор)
    requires java.desktop; // Добавлено для java.awt.geom

    // 3. ZXing (Штрихкоды и QR-коды)
    requires com.google.zxing;
    requires com.google.zxing.javase;

    // 4. Apache POI
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    // 5. Apache PDFBox
    requires org.apache.pdfbox;
    requires org.apache.pdfbox.io; // Добавлено для решения ошибки org.apache.pdfbox.io

    // 6. Log4j2
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    // 7. poi-tl
    requires poi.tl; 

    // Разрешаем JavaFX получать доступ к контроллерам интерфейса
    opens com.example to javafx.fxml;

    // Экспортируем основной пакет
    exports com.example;
}