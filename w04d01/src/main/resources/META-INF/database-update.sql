START TRANSACTION;

CREATE TABLE Manager (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE StoreManager (
  store_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  manager_id INTEGER NOT NULL,

  FOREIGN KEY fk_section(store_id) REFERENCES Store(id) ON DELETE CASCADE,
  FOREIGN KEY fk_section(manager_id) REFERENCES Manager(id) ON DELETE CASCADE
);

INSERT INTO Manager (id, name) VALUES (2, 'John');
INSERT INTO StoreManager(store_id, manager_id) VALUES (2, 2);
INSERT INTO StoreManager(store_id, manager_id) VALUES (3, 2);

COMMIT;
