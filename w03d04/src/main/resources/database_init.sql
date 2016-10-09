-- database initialization script

START TRANSACTION;

-- tables must be dropped in the reversed order of their FK relationships
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Section;
DROP TABLE IF EXISTS Store;
DROP TABLE IF EXISTS StoreType;
DROP PROCEDURE IF EXISTS getProductsCount;

CREATE TABLE StoreType (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30) NOT NULL
);

CREATE TABLE Store (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  location VARCHAR(20) NOT NULL,
  storeTypeId INTEGER,
  parentStoreId INTEGER DEFAULT NULL,

  FOREIGN KEY fk_parentStore(parentStoreId) REFERENCES Store(id) ON DELETE CASCADE,
  FOREIGN KEY fk_storeType(storeTypeId) REFERENCES StoreType(id) ON DELETE CASCADE
);

CREATE TABLE Section (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  storeId INTEGER NOT NULL,

  FOREIGN KEY fk_store(storeId) REFERENCES Store(id) ON DELETE CASCADE
);

CREATE TABLE Product (
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  sectionId INTEGER NOT NULL,

  FOREIGN KEY fk_section(sectionId) REFERENCES Section(id) ON DELETE CASCADE
);

CREATE PROCEDURE getProductsCount (OUT count INT)
  BEGIN
    SELECT COUNT(*) INTO count FROM Product;
  END;

INSERT INTO StoreType (id, name) VALUES (1, 'Electronics');
INSERT INTO StoreType (id, name) VALUES (2,     'Clothes');

INSERT INTO Store (id, name, location, storeTypeId, parentStoreId) VALUES (1,         'Mall',        'TM',  NULL, NULL);
INSERT INTO Store (id, name, location, storeTypeId, parentStoreId) VALUES (2, 'Media Galaxy', '1st floor',     1,    1);
INSERT INTO Store (id, name, location, storeTypeId, parentStoreId) VALUES (3,         'Zara', '2nd floor',     2,    2);

INSERT INTO Section (id, name, storeId) VALUES (1,   'Laptops', 2);
INSERT INTO Section (id, name, storeId) VALUES (2,  'Monitors', 2);
INSERT INTO Section (id, name, storeId) VALUES (3,  'T-shirts', 3);
INSERT INTO Section (id, name, storeId) VALUES (4,   'Blouses', 3);

INSERT INTO Product (id, name, sectionId) VALUES (1, 'Dell XPS 9360', 1);
INSERT INTO Product (id, name, sectionId) VALUES (2,    'Asus UX530', 1);
INSERT INTO Product (id, name, sectionId) VALUES (3, 'Samsung CF791', 2);
INSERT INTO Product (id, name, sectionId) VALUES (4,   'Dell P4317Q', 2);
INSERT INTO Product (id, name, sectionId) VALUES (5,   'White',       3);
INSERT INTO Product (id, name, sectionId) VALUES (6,   'Red',         3);

COMMIT;