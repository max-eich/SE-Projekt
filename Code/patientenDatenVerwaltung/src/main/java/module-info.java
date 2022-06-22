module com.administration.patientendatenverwaltung {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires com.fazecast.jSerialComm;

    opens com.administration to javafx.fxml,de.jensd.fx.glyphs.fontawesome,de.jensd.fx.glyphs.commons;
    exports com.administration;
    exports com.administration.frontend;
    opens com.administration.frontend to javafx.fxml, de.jensd.fx.glyphs.fontawesome, de.jensd.fx.glyphs.commons;
}