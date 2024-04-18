package org.example;

import lombok.Data;
import org.example.adnotations.DatabaseField;
import org.example.adnotations.DatabaseTable;

@Data
@DatabaseTable(keyColumn = "id")
public class User {
    @DatabaseField(columnName = "id")
    private Long id;
    @DatabaseField(columnName = "user_name")
    private String name;
    @DatabaseField(columnName = "user_surname")
    private String surname;
    private int age;
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
