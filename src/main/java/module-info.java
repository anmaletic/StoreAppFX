module hr.vsite.storeappfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    opens hr.vsite.storeappfx to javafx.fxml;
    opens hr.vsite.storeappfx.models to javafx.base;

    exports hr.vsite.storeappfx;
    exports hr.vsite.storeappfx.models;
}