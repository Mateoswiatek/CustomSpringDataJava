package org.example.model.adnotations;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class EntityProperties {
    String tableName;
    /**
     * key - nazwa pola w klasie, kodzie
     * value - klasa reprezentaujaca typ kolumny w bazie danych
     */
    Map<String,DatabaseColumn> fieldToColumns = new HashMap<>();

    //TODO future function
    Set<String> constraints;

    @Override
    public String toString() {
        return "EntityProperties{" +
                "\n  tableName=" + tableName +
                "\n  constraints=" + constraints +
                "\n  fieldToColumns=\n" + fieldToColumns +
                "\n}\n";
    }
}

