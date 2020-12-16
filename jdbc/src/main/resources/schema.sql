-- https://jira.spring.io/browse/datajdbc-338
CREATE TABLE IF NOT EXISTS "CUSTOMER"
(
    "ID"   VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID(),
    "NAME" VARCHAR(255) NOT NULL,
    "EMAIL" VARCHAR(255),
    "CREATED_DATE" DATETIME,
    "UPDATED_DATE" DATETIME,
    "VERSION" INTEGER NOT NULL,
    PRIMARY KEY ("ID")
);
CREATE TABLE IF NOT EXISTS "ORDER"
(
    "ID"         VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID(),
    "ORDER_DATE" DATE,
    "ORDER_NO"   VARCHAR(255),
    PRIMARY KEY ("ID")
);
CREATE TABLE IF NOT EXISTS "ORDER_LINE"
(
    "ID"        VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID(),
    "ORDER"     VARCHAR(36) NOT NULL,
    "ORDER_KEY" INTEGER NOT NULL,
    "NAME"      VARCHAR(255),
    "PRICE"     DECIMAL(19, 2),
    "QUANTITY"  INTEGER      NOT NULL,
    PRIMARY KEY ("ID")
);
