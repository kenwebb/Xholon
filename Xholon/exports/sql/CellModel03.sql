/* 
To view this file, download an open-source relational database product such as mysql or postgresql.

Automatically generated by Xholon version 0.9.1, using Xholon2Sql.java
Sat May 13 14:06:11 GMT-400 2017 1494698771950
model: Cell - BioSystems paper
www.primordion.com/Xholon
In MySQL, you will need to temporarily disable foreign keys while loading in this file:
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;
source thenameofthisfile.sql;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
 */
DROP TABLE IF EXISTS Hexokinase;
CREATE TABLE IF NOT EXISTS Hexokinase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES Glucose(ID),
 FOREIGN KEY (port3) REFERENCES Glucose_6_Phosphate(ID));
INSERT INTO Hexokinase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (19, '', 'Hexokinase', 1, 6, 8, 9);

DROP TABLE IF EXISTS PhosphoGlucoIsomerase;
CREATE TABLE IF NOT EXISTS PhosphoGlucoIsomerase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES Glucose_6_Phosphate(ID),
 FOREIGN KEY (port3) REFERENCES Fructose_6_Phosphate(ID));
INSERT INTO PhosphoGlucoIsomerase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (20, '', 'PhosphoGlucoIsomerase', 1, 6, 9, 10);

DROP TABLE IF EXISTS PhosphoFructokinase;
CREATE TABLE IF NOT EXISTS PhosphoFructokinase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES Fructose_6_Phosphate(ID),
 FOREIGN KEY (port3) REFERENCES Fructose_1x6_Biphosphate(ID));
INSERT INTO PhosphoFructokinase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (21, '', 'PhosphoFructokinase', 1, 6, 10, 11);

DROP TABLE IF EXISTS Aldolase;
CREATE TABLE IF NOT EXISTS Aldolase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 port4 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES Fructose_1x6_Biphosphate(ID),
 FOREIGN KEY (port3) REFERENCES Glyceraldehyde_3_Phosphate(ID),
 FOREIGN KEY (port4) REFERENCES DihydroxyacetonePhosphate(ID));
INSERT INTO Aldolase (ID, roleName, xhcName, pheneVal, parentID, port0, port3, port4) VALUES
 (22, '', 'Aldolase', 1, 6, 11, 13, 12);

DROP TABLE IF EXISTS TriosePhosphateIsomerase;
CREATE TABLE IF NOT EXISTS TriosePhosphateIsomerase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES DihydroxyacetonePhosphate(ID),
 FOREIGN KEY (port3) REFERENCES Glyceraldehyde_3_Phosphate(ID));
INSERT INTO TriosePhosphateIsomerase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (23, '', 'TriosePhosphateIsomerase', 1, 6, 12, 13);

DROP TABLE IF EXISTS Glyceraldehyde_3_phosphateDehydrogenase;
CREATE TABLE IF NOT EXISTS Glyceraldehyde_3_phosphateDehydrogenase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES Glyceraldehyde_3_Phosphate(ID),
 FOREIGN KEY (port3) REFERENCES X1x3_BisphosphoGlycerate(ID));
INSERT INTO Glyceraldehyde_3_phosphateDehydrogenase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (24, '', 'Glyceraldehyde_3_phosphateDehydrogenase', 1, 6, 13, 14);

DROP TABLE IF EXISTS PhosphoGlycerokinase;
CREATE TABLE IF NOT EXISTS PhosphoGlycerokinase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES X1x3_BisphosphoGlycerate(ID),
 FOREIGN KEY (port3) REFERENCES X3_PhosphoGlycerate(ID));
INSERT INTO PhosphoGlycerokinase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (25, '', 'PhosphoGlycerokinase', 1, 6, 14, 15);

DROP TABLE IF EXISTS PhosphoGlyceromutase;
CREATE TABLE IF NOT EXISTS PhosphoGlyceromutase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES X3_PhosphoGlycerate(ID),
 FOREIGN KEY (port3) REFERENCES X2_PhosphoGlycerate(ID));
INSERT INTO PhosphoGlyceromutase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (26, '', 'PhosphoGlyceromutase', 1, 6, 15, 16);

DROP TABLE IF EXISTS Enolase;
CREATE TABLE IF NOT EXISTS Enolase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES X2_PhosphoGlycerate(ID),
 FOREIGN KEY (port3) REFERENCES PhosphoEnolPyruvate(ID));
INSERT INTO Enolase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (27, '', 'Enolase', 1, 6, 16, 17);

DROP TABLE IF EXISTS PyruvateKinase;
CREATE TABLE IF NOT EXISTS PyruvateKinase (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 port0 int,
 port3 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES PhosphoEnolPyruvate(ID),
 FOREIGN KEY (port3) REFERENCES Pyruvate(ID));
INSERT INTO PyruvateKinase (ID, roleName, xhcName, pheneVal, parentID, port0, port3) VALUES
 (28, '', 'PyruvateKinase', 1, 6, 17, 18);

DROP TABLE IF EXISTS EukaryoticCell;
CREATE TABLE IF NOT EXISTS EukaryoticCell (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 parentID int /*  FOREIGN KEY (parentID) REFERENCES ExtraCellularSpace(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO EukaryoticCell (ID, roleName, xhcName, parentID) VALUES
 (3, '', 'EukaryoticCell', 0);

DROP TABLE IF EXISTS CellBilayer;
CREATE TABLE IF NOT EXISTS CellBilayer (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 parentID int /*  FOREIGN KEY (parentID) REFERENCES CellMembrane(ID) */ ,
 port0 int,
 port4 int,
 PRIMARY KEY  (ID),
 FOREIGN KEY (port0) REFERENCES Glucose(ID),
 FOREIGN KEY (port4) REFERENCES Glucose(ID));
INSERT INTO CellBilayer (ID, roleName, xhcName, parentID, port0, port4) VALUES
 (5, '', 'CellBilayer', 4, 2, 8);

DROP TABLE IF EXISTS CellMembrane;
CREATE TABLE IF NOT EXISTS CellMembrane (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 parentID int /*  FOREIGN KEY (parentID) REFERENCES EukaryoticCell(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO CellMembrane (ID, roleName, xhcName, parentID) VALUES
 (4, '', 'CellMembrane', 3);

DROP TABLE IF EXISTS Glucose;
CREATE TABLE IF NOT EXISTS Glucose (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES ExtraCellularSolution(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Glucose (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (2, '', 'Glucose', 100000, 1);
INSERT INTO Glucose (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (8, '', 'Glucose', 100000, 7);

DROP TABLE IF EXISTS Pyruvate;
CREATE TABLE IF NOT EXISTS Pyruvate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Pyruvate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (18, '', 'Pyruvate', 100000, 7);

DROP TABLE IF EXISTS DihydroxyacetonePhosphate;
CREATE TABLE IF NOT EXISTS DihydroxyacetonePhosphate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO DihydroxyacetonePhosphate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (12, '', 'DihydroxyacetonePhosphate', 100000, 7);

DROP TABLE IF EXISTS Fructose_1x6_Biphosphate;
CREATE TABLE IF NOT EXISTS Fructose_1x6_Biphosphate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Fructose_1x6_Biphosphate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (11, '', 'Fructose_1x6_Biphosphate', 100000, 7);

DROP TABLE IF EXISTS Fructose_6_Phosphate;
CREATE TABLE IF NOT EXISTS Fructose_6_Phosphate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Fructose_6_Phosphate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (10, '', 'Fructose_6_Phosphate', 100000, 7);

DROP TABLE IF EXISTS Glucose_6_Phosphate;
CREATE TABLE IF NOT EXISTS Glucose_6_Phosphate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Glucose_6_Phosphate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (9, '', 'Glucose_6_Phosphate', 100000, 7);

DROP TABLE IF EXISTS Glyceraldehyde_3_Phosphate;
CREATE TABLE IF NOT EXISTS Glyceraldehyde_3_Phosphate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Glyceraldehyde_3_Phosphate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (13, '', 'Glyceraldehyde_3_Phosphate', 100000, 7);

DROP TABLE IF EXISTS PhosphoEnolPyruvate;
CREATE TABLE IF NOT EXISTS PhosphoEnolPyruvate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO PhosphoEnolPyruvate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (17, '', 'PhosphoEnolPyruvate', 100000, 7);

DROP TABLE IF EXISTS X1x3_BisphosphoGlycerate;
CREATE TABLE IF NOT EXISTS X1x3_BisphosphoGlycerate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO X1x3_BisphosphoGlycerate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (14, '', 'X1x3_BisphosphoGlycerate', 100000, 7);

DROP TABLE IF EXISTS X2_PhosphoGlycerate;
CREATE TABLE IF NOT EXISTS X2_PhosphoGlycerate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO X2_PhosphoGlycerate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (16, '', 'X2_PhosphoGlycerate', 100000, 7);

DROP TABLE IF EXISTS X3_PhosphoGlycerate;
CREATE TABLE IF NOT EXISTS X3_PhosphoGlycerate (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 pheneVal int,
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytosol(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO X3_PhosphoGlycerate (ID, roleName, xhcName, pheneVal, parentID) VALUES
 (15, '', 'X3_PhosphoGlycerate', 100000, 7);

DROP TABLE IF EXISTS Cytosol;
CREATE TABLE IF NOT EXISTS Cytosol (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 parentID int /*  FOREIGN KEY (parentID) REFERENCES Cytoplasm(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Cytosol (ID, roleName, xhcName, parentID) VALUES
 (7, '', 'Cytosol', 6);

DROP TABLE IF EXISTS ExtraCellularSolution;
CREATE TABLE IF NOT EXISTS ExtraCellularSolution (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 parentID int /*  FOREIGN KEY (parentID) REFERENCES ExtraCellularSpace(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO ExtraCellularSolution (ID, roleName, xhcName, parentID) VALUES
 (1, '', 'ExtraCellularSolution', 0);

DROP TABLE IF EXISTS Cytoplasm;
CREATE TABLE IF NOT EXISTS Cytoplasm (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 parentID int /*  FOREIGN KEY (parentID) REFERENCES EukaryoticCell(ID) */ ,
 PRIMARY KEY  (ID));
INSERT INTO Cytoplasm (ID, roleName, xhcName, parentID) VALUES
 (6, '', 'Cytoplasm', 3);

DROP TABLE IF EXISTS ExtraCellularSpace;
CREATE TABLE IF NOT EXISTS ExtraCellularSpace (
 ID int NOT NULL auto_increment,
 roleName varchar(80) NOT NULL default '',
 xhcName varchar(80) NOT NULL default '',
 PRIMARY KEY  (ID));
INSERT INTO ExtraCellularSpace (ID, roleName, xhcName) VALUES
 (0, '', 'ExtraCellularSpace');


