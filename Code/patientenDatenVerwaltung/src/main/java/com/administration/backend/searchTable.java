package com.administration.backend;

import javafx.beans.property.SimpleStringProperty;

public class searchTable {
    private SimpleStringProperty PatientID;
    public SimpleStringProperty Name;
    public SimpleStringProperty Geschlecht;
    public SimpleStringProperty Geburtsdatum;
    public SimpleStringProperty Zimmernummer;

    public searchTable(String patientID, String name, String geschlecht, String geburtsdatum, String zimmernummer) {
        PatientID = new SimpleStringProperty(patientID);
        Name = new SimpleStringProperty(name);
        Geschlecht = new SimpleStringProperty(geschlecht);
        Geburtsdatum = new SimpleStringProperty(geburtsdatum);
        Zimmernummer = new SimpleStringProperty(zimmernummer);
    }
}
