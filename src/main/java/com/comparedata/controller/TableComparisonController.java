package com.comparedata.controller;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comparedata.service.ComparisonResponse;
import com.comparedata.service.DataCompareService;
import com.comparedata.service.TableComparisonService;

@RestController
public class TableComparisonController {

    @Autowired
    private TableComparisonService tableComparisonService;

    @Autowired
    private  DataCompareService dataCompareService;

    
    public TableComparisonController(DataCompareService dataCompareService) {
        this.dataCompareService = dataCompareService;
    }
    
    @GetMapping("/compare-tables2")
    public String compareTables2(@RequestParam String table1, @RequestParam String table2) throws SQLException {
        return tableComparisonService.getTableColumns2(table1);
    }
    
    @GetMapping("/get-data")
    public List<Map<String, Object>> getData(@RequestParam String tableName) {
        return tableComparisonService.getAllData(tableName);
    }
    

    @GetMapping("/get-data-two-tables")
    public Map<String, List<Map<String, Object>>> getDataFromTwoTables(@RequestParam String table1, @RequestParam String table2) {
        return tableComparisonService.getAllDataFromTwoTables(table1, table2);
    }
    
    @GetMapping("/compare-tables")
    public Map<String, Object> compareTables(@RequestParam String table1, @RequestParam String table2) {
        return tableComparisonService.compareTables(table1, table2);
    }
    
//    @GetMapping("/primary-keys")
//    public List<String> getPrimaryKeys(@RequestParam String tableName) {
//        return tableMetadataService.getPrimaryKeys(tableName);
//    }
    
    
    @GetMapping("/compare-tables3")
    public ComparisonResponse compareTables3(@RequestParam String table1, @RequestParam String table2) {
        return dataCompareService.compareTables(table1, table2);
    }
}
