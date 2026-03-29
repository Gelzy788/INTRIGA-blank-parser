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

    // 8. json
    requires org.json;

    // Разрешаем JavaFX получать доступ к корневому пакету (где лежит Main.java)
    opens com.example to javafx.fxml;
    exports com.example;

    // Разрешения для нового пакета фронтенда
    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;
}