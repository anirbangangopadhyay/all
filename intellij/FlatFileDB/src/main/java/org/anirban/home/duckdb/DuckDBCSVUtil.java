package org.anirban.home.duckdb;

import org.anirban.home.Investment;

import java.io.FileNotFoundException;
import java.net.URL;

public final class DuckDBCSVUtil {

    public static String getTable(String csvResource) {
        URL csvResourceUrl = DuckDBCSVUtil.class.getClassLoader().getResource(csvResource);
        if(csvResourceUrl == null) {
            throw new RuntimeException(new FileNotFoundException("The resource file is not found"));
        }
        return csvResourceUrl.getPath();
    }
}
