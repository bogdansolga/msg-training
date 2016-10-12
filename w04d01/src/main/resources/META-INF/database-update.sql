START TRANSACTION;

DROP TABLE IF EXISTS StoreManager;
DROP TABLE IF EXISTS Manager;

CREATE TABLE Manager (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE StoreManager (
  store_id INTEGER NOT NULL,
  manager_id INTEGER NOT NULL,

  PRIMARY KEY (store_id, manager_id),

  FOREIGN KEY fk_store(store_id) REFERENCES Store(id) ON DELETE CASCADE,
  FOREIGN KEY fk_manager(manager_id) REFERENCES Manager(id) ON DELETE CASCADE
);

INSERT INTO Manager (id, name) VALUES (2, 'John');
INSERT INTO StoreManager(store_id, manager_id) VALUES (2, 2);
INSERT INTO StoreManager(store_id, manager_id) VALUES (3, 2);

COMMIT;
