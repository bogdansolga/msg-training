package com.msgsystems.training.w03d04.repository;

import com.msgsystems.training.w03d04.model.Product;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ProductRepository {

    private static final String DB_PROPERTIES_FILE = "database-connection.properties";
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASS;

    static {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try (final InputStream inputStream = contextClassLoader.getResourceAsStream(DB_PROPERTIES_FILE)) {
            final Properties properties = new Properties();
            properties.load(inputStream);

            DB_URL = properties.getProperty("database.url");
            DB_USER = properties.getProperty("database.user");
            DB_PASS = properties.getProperty("database.pass");
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public boolean updateProductSection(int oldSectionId, int newSectionId) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            final String sql = "UPDATE Product SET sectionId = " + newSectionId + " WHERE sectionId = " + oldSectionId;
            int updatedRecords = statement.executeUpdate(sql);
            System.out.println("There were " + updatedRecords + " products moved from section " + oldSectionId + " to section " + newSectionId);
            return updatedRecords > 0;
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
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
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
