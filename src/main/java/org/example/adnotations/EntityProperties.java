package org.example.adnotations;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class EntityProperties {
    String tableName;
    Map<String,databaseColumn> fieldToColumns;

    //TODO future function
    Set<String> constraints;
}

@Data
class databaseColumn{
    String columnName;
    String columnType;
}
