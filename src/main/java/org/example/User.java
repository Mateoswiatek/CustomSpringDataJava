package org.example;

import lombok.Data;
import org.example.adnotations.databasecreator.DatabaseField;
import org.example.adnotations.databasecreator.DatabaseTable;

@Data
@DatabaseTable(keyColumn = "id")
public class User {
    @DatabaseField(columnName = "id")
    private Long id;
    @DatabaseField(columnName = "user_NAME")
    private String name;
    @DatabaseField(columnName = "user_surname", columnType = "VARCHAR(4000)")
    private String surname;
    private int age;
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
