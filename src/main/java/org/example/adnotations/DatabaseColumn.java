package org.example.adnotations;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DatabaseColumn{
    String columnName;
    String columnType;

    public DatabaseColumn(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    @Override
    public String toString() {
        return "DatabaseColumn{" +
                "\n  columnName=" + columnName +
                "\n  columnType=" + columnType +
                "\n}\n";
    }
}
