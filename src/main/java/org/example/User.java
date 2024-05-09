package org.example;

import lombok.Data;
import org.example.model.adnotations.databasecreator.DatabaseField;
import org.example.model.adnotations.databasecreator.DatabaseTable;

@Data
@DatabaseTable(tableName = "user", keyColumn = "id")
public class User {
    @DatabaseField(columnName = "id")
    private Long id;
    @DatabaseField(columnName = "user_name")
    private String name;
    @DatabaseField(columnName = "user_surname", columnType = "VARCHAR(4000)")
    private String surname;
    private int age;
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
