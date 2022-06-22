
CREATE TABLE IF NOT EXISTS Patient
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patientID INT,
    vorname STRING,
    nachname STRING,
    geburtstag STRING,
    geschlecht STRING    
);

CREATE TABLE IF NOT EXISTS Unterbringung
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    zimmer STRING,
    entlassung STRING,
    einlieferung STRING,
    aenderung DATE,
    Patient_id INT,
    User_id INT
);

CREATE TABLE IF NOT EXISTS User
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name STRING,
    role STRING,
    password STRING
);

CREATE TABLE IF NOT EXISTS Card
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cardID INT,
    rfid STRING,
    User_id INT
);

CREATE TABLE IF NOT EXISTS Einrichtungen
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ID INT,
    art STRING,
    name STRING,
    adresse STRING,
    telefonnummer STRING,
    aenderung DATE,
    Patient_id INT,
    User_id INT
    
);

CREATE TABLE IF NOT EXISTS Stammdaten
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    strasse STRING,
    ort STRING,
    plz STRING,
    land STRING,
    telefon STRING,
    handy STRING,
    eMail STRING,
    kostentraeger STRING,
    versicherungsnummer INT,
    aenderung DATE,
    Patient_id INT,
    User_id INT
    
);

CREATE TABLE IF NOT EXISTS Krankheit
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type STRING,
    icd10 STRING,
    beschreibung STRING,
    erstellung DATE,
    arzt STRING,
    Patient_id INT,
    User_id INT
);

CREATE TABLE IF NOT EXISTS Anamnese
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    groesse INT,
    gewicht INT,
    behinderung BOOLEAN,
    grad DOUBLE,
    chronischeKrankheit STRING,
    aenderung DATE,
    Patient_id INT,
    User_id INT
    
);

CREATE TABLE IF NOT EXISTS AdipositasSyndrom
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    LaurenceMoonBardetBiedel BOOLEAN,
    PraderWilli BOOLEAN,
    SimpsonGolabiBehmel BOOLEAN,
    Sotos BOOLEAN,
    `Trisomie 21` BOOLEAN,
    Weaver BOOLEAN,
    andere STRING,
    Anamnese_id INT
    
);

CREATE TABLE IF NOT EXISTS AdipositasMedikamente
(
    id INTEGER PRIMARY KEY  AUTOINCREMENT,
    Glukokortikoide BOOLEAN,
    Insulingabe BOOLEAN,
    Valproat BOOLEAN,
    Phenothiazine BOOLEAN,
    andere STRING,
    Anamnese_id INT
    
);

CREATE TABLE IF NOT EXISTS Endokrinologisch
(
    id INTEGER PRIMARY KEY  AUTOINCREMENT,
    `Adipositas bei Hypothyreose` BOOLEAN,
    CushingSyndrom BOOLEAN,
    `genetisch bedingter Leptinmangel` BOOLEAN,
    Kraniopharyngeom BOOLEAN,
    Leptirezeptormutation BOOLEAN,
    MC4Rezeptormutationen BOOLEAN,
    `Morbus Cushing` BOOLEAN,
    POMCMutationen BOOLEAN,
    `primärer Hyperinsulinismus/WiedemannBeckwith` BOOLEAN,
    STHMangel BOOLEAN,
    Anamnese_id INT
    
);

