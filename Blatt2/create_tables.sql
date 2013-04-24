-- Erstellung der Tabellen:
CREATE TABLE Makler 
(
	Name varchar(100),
	Adresse varchar(100), 
	Login varchar(100) NOT NULL PRIMARY KEY, 
	Passwort varchar(100) NOT NULL
);

CREATE TABLE Wohnung 
(
	ID int PRIMARY KEY NOT NULL, 
	MLogin varchar(100), --Fremdschlüssel
	Ort varchar(100), 
	PLZ varchar(10), 
	Strasse varchar(100), 
	HausNr int, 
	Flaeche int,
	Stockwerk int,
	Mietpreis int,
	Zimmer int,
	Balkon Char(1) NOT NULL Default 'N',
	EBK Char(1) NOT NULL Default 'N',

Constraint BoolCheckBalkon Check (Balkon IN ('Y', 'N')),
Constraint BoolCheckEBK Check (EBK IN ('Y', 'N'))
);

CREATE TABLE Haus 
(
	ID int PRIMARY KEY NOT NULL, 
	MLogin varchar(100), --Fremdschlüssel
	Ort varchar(100), 
	PLZ varchar(10), 
	Strasse varchar(100), 
	HausNr int, 
	Flaeche int,
	Stockwerke int,
	Kaufpreis int,
	Garten Char(1) NOT NULL Default 'N',

Constraint BoolCheck Check (Garten IN ('Y', 'N'))
);

CREATE TABLE Person 
(
	PID int PRIMARY KEY NOT NULL,
	Vorname varchar(100), 
	Nachname varchar(100), 
	Adresse varchar(100))

;

CREATE TABLE Mietvertrag 
(
	Vertragsnummer int PRIMARY KEY NOT NULL, 
	Datum int, 
	Ort varchar(100),
	Mietbeginn int,
	Dauer int,
	Nebenkosten int,
	PID int, --PersonenID
	HausID int, --HausID
	WohnungID int --WohnungID
);

CREATE TABLE Kaufvertrag 
(
	Vertragsnummer int PRIMARY KEY NOT NULL, 
	Datum int, 
	Ort varchar(100),
	AnzahlRaten int,
	Ratenzins int,
	PID int, --PersonenID
	HausID int, --HausID
	WohnungID int --WohnungID
);

--Einpfelegen der Verbindungen:
ALTER TABLE "VSISP17"."WOHNUNG"
	ADD CONSTRAINT "VERWALTETWOHNUNG"
	FOREIGN KEY ("MLOGIN") REFERENCES "VSISP17"."MAKLER" ("LOGIN")
;

ALTER TABLE "VSISP17"."HAUS"
	ADD CONSTRAINT "VERWALTETHAUS"
	FOREIGN KEY ("MLOGIN") REFERENCES "VSISP17"."MAKLER" ("LOGIN")
;

ALTER TABLE "VSISP17"."MIETVERTRAG"
	ADD CONSTRAINT "VERMIETET1"
	FOREIGN KEY ("PID") REFERENCES "VSISP17"."PERSON" ("PID")
;

ALTER TABLE "VSISP17"."MIETVERTRAG"
	ADD CONSTRAINT "VERMIETET2"
	FOREIGN KEY ("IMMOID") REFERENCES "VSISP17"."WOHNUNG" ("ID")
;

ALTER TABLE "VSISP17"."KAUFVERTRAG"
	ADD CONSTRAINT "VERKAUFT1"
	FOREIGN KEY ("PID") REFERENCES "VSISP17"."PERSON" ("PID")
;

ALTER TABLE "VSISP17"."KAUFVERTRAG"
	ADD CONSTRAINT "VERKAUFT2"
	FOREIGN KEY ("IMMOID") REFERENCES "VSISP17"."HAUS" ("ID")
;

-- Beispiel Markler (admin)
INSERT INTO "VSISP17"."MAKLER"
(NAME, ADRESSE, LOGIN, PASSWORT)
VALUES
('Max Mustermann', 'Musterstadt', 'admin', 'admin')
;
