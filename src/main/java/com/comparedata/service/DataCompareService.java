package com.comparedata.service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DataCompareService {

    private final JdbcTemplate jdbcTemplate;

    public DataCompareService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ComparisonResponse compareTables(String table1, String table2) {
        List<Map<String, Object>> table1Data = getAllData(table1);
        List<Map<String, Object>> table2Data = getAllData(table2);

        List<Map<String, Object>> differentData = new ArrayList<>();
        List<Map<String, Object>> user2Data = new ArrayList<>(table2Data);
        List<Map<String, Object>> userData = new ArrayList<>(table1Data);

        // Find differences
        for (Map<String, Object> row1 : table1Data) {
            for (Map<String, Object> row2 : table2Data) {
                // Assuming 'id' is the primary key in your tables
                if (row1.get("id").equals(row2.get("id"))) {
                    Map<String, Object> diffRow = new HashMap<>();
                    boolean isDifferent = false;

                    for (String key : row1.keySet()) {
                        Object value1 = row1.get(key);
                        Object value2 = row2.get(key);
                        if (!value1.equals(value2)) {
                            isDifferent = true;
                            diffRow.put("diff_" + key, value2); // Add diff_ prefix for differing column
                        } else {
                            diffRow.put(key, value1); // Keep the same value if not different
                        }
                    }

                    if (isDifferent) {
                        diffRow.put("id", row1.get("id")); // Keep the primary key
                        differentData.add(diffRow);
                    }
                }
            }
        }

        int totalItems = table1Data.size() + table2Data.size();
        int totalDifferent = differentData.size();

        return new ComparisonResponse(totalItems, totalDifferent, table1, table2, differentData, user2Data, userData);
    }

    private List<Map<String, Object>> getAllData(String tableName) {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try {
            rows = jdbcTemplate.query(query, (rs, rowNum) -> {
                Map<String, Object> row = new HashMap<>();
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    row.put(columnName, rs.getObject(columnName));
                }
                return row;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}
