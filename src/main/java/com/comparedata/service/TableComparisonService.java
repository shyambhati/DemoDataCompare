package com.comparedata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TableComparisonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
 
  

  
    
    // Method to retrieve column names dynamically for a table
    
    public String getTableColumns2(String tableName) throws SQLException {
    	
    	Connection connection = jdbcTemplate.getDataSource().getConnection();
    			
    
    	 List<String> tableNames = new ArrayList<>();
        
         DatabaseMetaData metaData = connection.getMetaData();
         ResultSet resultSet = metaData.getTables("datacompare", null, "%", new String[]{"TABLE"});
         while (resultSet.next()) {
             tableNames.add(resultSet.getString("TABLE_NAME"));
             System.out.println(resultSet.getString("TABLE_NAME"));
         }
         
         getColumns("user");
         
         getAllData("user");
       
    	return "";
    }
    
    
    public List<String> getColumns(String tableName) {
        List<String> columns = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             ResultSet resultSet = connection.getMetaData().getColumns("datacompare", null, tableName, null)) { // Specify the database name here

            while (resultSet.next()) {
                columns.add(resultSet.getString("COLUMN_NAME"));
                System.out.println(resultSet.getString("COLUMN_NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columns;
    }
    
    
 // Method to get all data from two tables
    public Map<String, Object> compareTables(String tableName1, String tableName2) {
        List<Map<String, Object>> data1 = getAllData(tableName1);
        List<Map<String, Object>> data2 = getAllData(tableName2);

        Map<String, Object> comparisonResult = new HashMap<>();
        comparisonResult.put("table1", tableName1);
        comparisonResult.put("table2", tableName2);

        // Find differences
        List<Map<String, Object>> differences = new ArrayList<>();
        Set<String> keys1 = new HashSet<>();
        Set<String> keys2 = new HashSet<>();

        for (Map<String, Object> row : data1) {
            keys1.add(row.get("id").toString());
        }

        for (Map<String, Object> row : data2) {
            keys2.add(row.get("id").toString());
        }

        // Get IDs that are in table1 but not in table2
        for (Map<String, Object> row : data1) {
            String id = row.get("id").toString();
            if (!keys2.contains(id)) {
                differences.add(row);
            }
        }

        // Get IDs that are in table2 but not in table1
        for (Map<String, Object> row : data2) {
            String id = row.get("id").toString();
            if (!keys1.contains(id)) {
                differences.add(row);
            }
        }

        // Prepare response
        comparisonResult.put("different", differences);
        comparisonResult.put("totalItems", data1.size() + data2.size());
        comparisonResult.put("totalDifferent", differences.size());

        return comparisonResult;
    }
    
    // Method to get all data from two tables
    public Map<String, List<Map<String, Object>>> getAllDataFromTwoTables(String tableName1, String tableName2) {
        Map<String, List<Map<String, Object>>> tablesData = new HashMap<>();
        tablesData.put(tableName1, getAllData(tableName1));
        tablesData.put(tableName2, getAllData(tableName2));
        return tablesData;
    }
    
    public List<Map<String, Object>> getAllData(String tableName) {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try {
        	rows = jdbcTemplate.query(query, (RowMapper<Map<String, Object>>) (rs, rowNum) -> {
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
