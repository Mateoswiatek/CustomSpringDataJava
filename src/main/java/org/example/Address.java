package org.example;

import lombok.Data;
import org.example.model.adnotations.databasecreator.DatabaseField;
import org.example.model.adnotations.databasecreator.DatabaseTable;

@Data
@DatabaseTable(tableName = "address", keyColumn = "id")
public class Address {
    @DatabaseField(columnName = "id")
    private Long id;
    @DatabaseField(columnName = "user_name")
    private String name;
    @DatabaseField(columnName = "address_street")
    private String street;
}
