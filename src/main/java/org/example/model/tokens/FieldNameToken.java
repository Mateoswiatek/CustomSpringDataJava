package org.example.model.tokens;

public class FieldNameToken implements TokenInterface{
    private String fieldName;
    private String tableName;

    public String nameOfField() {
        return "field";
    }
}
