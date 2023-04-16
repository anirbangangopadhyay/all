package org.anirban.home.duckdb.embedded;

import org.duckdb.DuckDBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DuckDBEmbeddedUtil {

    private static final Logger LOGGER = Logger.getLogger(DuckDBEmbeddedUtil.class.getName());

    private static final Properties CONNECTION_PROPERTIES;
    private static final DuckDBConnection CONNECTION;

    static {
        try {
            LOGGER.log(Level.FINE, "Starting singleton embedded connection to DuckDB Embedded!");
            Class.forName("org.duckdb.DuckDBDriver");
            CONNECTION_PROPERTIES = new Properties();
            CONNECTION = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:", CONNECTION_PROPERTIES);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }

    public static void closeConnection() {
        try {
            if(!CONNECTION.isClosed()) {
                LOGGER.log(Level.FINE, "Closing singleton embedded connection to DuckDB Embedded!");
                CONNECTION.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        closeConnection();
    }
}
