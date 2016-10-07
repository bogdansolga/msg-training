package com.msgsystems.training.w03d05.repository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ProductRepository {

    private static final String DB_PROPERTIES_FILE = "database-connection.properties";

    private final static ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    private static String DB_URL;
    private static String DB_USER;

    private static String DB_PASS;

    static {
        try (final InputStream inputStream = loadFile(DB_PROPERTIES_FILE)) {
            final Properties properties = new Properties() {{
                load(inputStream);
            }};

            DB_URL = properties.getProperty("database.url");
            DB_USER = properties.getProperty("database.user");
            DB_PASS = properties.getProperty("database.pass");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int saveBLOB(final String fileName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final int id = getNextProductDataSequenceId();

        try (final InputStream inputStream = loadFile(fileName)) {
            connection = getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO ProductData (id, image) VALUES (?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setBinaryStream(2, inputStream);
            preparedStatement.executeUpdate();
        } catch (final Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } finally {
            closeResources(connection, null, preparedStatement);
        }

        return id;
    }

    public int saveCLOB(final String fileName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final int id = getNextProductDataSequenceId();

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO ProductData (id, description) VALUES (?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setCharacterStream(2, new InputStreamReader(loadFile(fileName)));
            preparedStatement.executeUpdate();
        } catch (final Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } finally {
            closeResources(connection, null, preparedStatement);
        }

        return id;
    }

    public InputStream loadSavedImage(int id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT image FROM ProductData WHERE ID = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBinaryStream("image");
            } else {
                throw new IllegalArgumentException("There is no image with the ID " + id);
            }
        } catch (final SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } finally {
            closeResources(connection, resultSet, preparedStatement);
        }
    }

    public Reader loadSavedDescription(int id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT description FROM ProductData WHERE ID = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getCharacterStream("description");
            } else {
                throw new IllegalArgumentException("There is no image with the ID " + id);
            }
        } catch (final SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } finally {
            closeResources(connection, resultSet, preparedStatement);
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
            if (connection != null) connection.close();
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    private static InputStream loadFile(String loadedFile) {
        return CLASS_LOADER.getResourceAsStream(loadedFile);
    }

    private int getNextProductDataSequenceId() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("UPDATE ProductDataSequence SET id = LAST_INSERT_ID(id + 1)");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new IllegalArgumentException("Cannot get the next sequenceId" );
            }
        } catch (final SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } finally {
            closeResources(connection, resultSet, preparedStatement);
        }
    }
}
