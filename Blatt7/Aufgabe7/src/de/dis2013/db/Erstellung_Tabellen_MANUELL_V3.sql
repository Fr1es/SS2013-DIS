CREATE TABLE vsisp17.DimDay
(
dayID varchar(50) NOT NULL,
month varchar(50),
quarter varchar(50),
year varchar(50),
primary key (dayID)
);

CREATE TABLE vsisp17.DimShop
(
shopID INTEGER NOT NULL,
name varchar(50),
town varchar(50),
region varchar(50),
country varchar(50),
primary key (shopID)
);

CREATE TABLE vsisp17.DimArticle
(
articleID INTEGER NOT NULL,
name varchar(50),
productGroup varchar(50),
productFamily varchar(50),
productCategory varchar(50),
primary key (articleid)
);

CREATE TABLE vsisp17.Fact
(
DayID varchar(50) NOT NULL,
shopID INTEGER NOT NULL,
articleID INTEGER NOT NULL,
sold INTEGER,
turnover INTEGER,
primary key (dayid, shopid, articleid)
);

ALTER TABLE "VSISP17"."FACT"
ADD CONSTRAINT FOREIGNKEY
FOREIGN KEY (DayID)
REFERENCES "VSISP17"."DIMDAY"(DAYID)
;

ALTER TABLE "VSISP17"."FACT"
ADD CONSTRAINT FOREIGNKEYSHOP
FOREIGN KEY (shopID)
REFERENCES "VSISP17"."DIMSHOP"(shopID)
;

ALTER TABLE "VSISP17"."FACT"
ADD CONSTRAINT FOREIGNKEYARTICLE
FOREIGN KEY (ARTICLEID)
REFERENCES "VSISP17"."DIMARTICLE"(ARTICLEID);