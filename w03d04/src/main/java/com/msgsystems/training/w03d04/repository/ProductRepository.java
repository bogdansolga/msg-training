package com.msgsystems.training.w03d04.repository;

import com.msgsystems.training.w03d04.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductRepository {

    private static final String DB_URL = "jdbc:mysql://localhost/msg_training";
    private static final String USER = "root";
    private static final String PASS = "mysql";

    private static final boolean INITIALIZE = false;

    public ProductRepository() {
        if (!INITIALIZE) return;

        Connection connection = null;
        try {
            connection = getConnection();

            connection.prepareCall("DROP TABLE IF EXISTS Product").execute();
            connection.prepareCall("CREATE TABLE Product(id INTEGER NOT NULL, name CHAR(30), PRIMARY KEY (id))").execute();

            connection.prepareCall("INSERT INTO Product (id, name) VALUES (1, 'Dell XPS')").execute();
            connection.prepareCall("INSERT INTO Product (id, name) VALUES (2, 'Asus UX530')").execute();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, null, null);
        }
    }

    public int getCount() {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT count(*) FROM Product");
            resultSet.next();
            return resultSet.getInt(1);
        } catch (final SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } finally {
            closeResources(connection, resultSet, statement);
        }
    }

    public Product getProduct(final int id) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM Product WHERE ID = " + id);
            if (resultSet.next()) {
                return new Product(resultSet.getInt("id"), resultSet.getString("name"));
            } else {
                throw new IllegalArgumentException("There is no product with the ID " + id);
            }
        } catch (final SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } finally {
            closeResources(connection, resultSet, statement);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void closeResources(final Connection connection, final ResultSet resultSet, final Statement statement) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
