/** column_name data_type, ... **/
CREATE TABLE Wohnung (ID int, Ort varchar(100), PLZ int, Strasse varchar(100), HausNr int, Flaeche float); /* fehlt noch spezielles */
CREATE TABLE Haus (ID int, Ort varchar(100), PLZ int, Strasse varchar(100), HausNr int, Flaeche float); /* fehlt noch spezielles */
CREATE TABLE Makler (Name varchar(100) NOT NULL, Adresse varchar(100), Login varchar(100) NOT NULL PRIMARY KEY, Passwort varchar(100) NOT NULL);
CREATE TABLE Person (Vorname varchar(100), Nachname varchar(100), Adresse varchar(100));
CREATE TABLE Mietvertrag (Vertragsnummer int, Datum varchar(100), Ort varchar(100)); /* fehlt noch spezielles */
CREATE TABLE Kaufvertrag (Vertragsnummer int, Datum varchar(100), Ort varchar(100)); /* fehlt noch spezielles */
