package org.example.adnotations;

import java.lang.reflect.Field;
import java.util.HashMap;

public class DataBaseCreator {
    private HashMap<Class<?>, String> typeMapper = new HashMap<>();

    public DataBaseCreator() {
        typeMapper.put(int.class, "INTEGER");
        typeMapper.put(long.class, "BIGINT");
        typeMapper.put(double.class, "DOUBLE PRECISION");
        typeMapper.put(float.class, "REAL");
        typeMapper.put(boolean.class, "BOOLEAN");
        typeMapper.put(Integer.class, "INTEGER");
        typeMapper.put(Long.class, "BIGINT");
        typeMapper.put(Double.class, "DOUBLE");
        typeMapper.put(String.class, "VARCHAR(200)");
        typeMapper.put(Boolean.class, "BOOLEAN");
    }

    private String tableName;

    public void createTable(Class myClass) {
        if(!myClass.isAnnotationPresent(DatabaseTable.class)){ throw new RuntimeException("Klasa nie ma adnotacji"); }
        DatabaseTable databaseTable = (DatabaseTable) myClass.getAnnotation(DatabaseTable.class);
        tableName = databaseTable.name().isEmpty() ? myClass.getSimpleName().toLowerCase() : databaseTable.name();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ").append(tableName).append(" (\n");

        for(Field field : myClass.getDeclaredFields()){
            if(field.isAnnotationPresent(DatabaseField.class)) {
                stringBuilder.append(createTableColumn(field));
            }
        }


        if (!stringBuilder.isEmpty()) {
            int length = stringBuilder.length();
            stringBuilder.deleteCharAt( length- 1);
            stringBuilder.deleteCharAt(length - 2);
        }
        stringBuilder.append("\n)");
        String out = stringBuilder.toString();
        System.out.println(out);
    }

    private String createTableColumn(Field field){
        DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        return "    " + databaseField.columnName() + " " + (databaseField.columnType().isEmpty() ? typeMapper.get(field.getType()) : databaseField.columnType()) + ",\n";
    }

    public String addColumn(DatabaseField databaseField) {

        System.out.println("Column name: " + databaseField.columnName()
                + " | Clumn type: " + databaseField.columnType());

        String columnName = databaseField.columnName();
        String columnType = databaseField.columnType();
        return String.format("ALTER TABLE %s ADD %s %s;\n", tableName, columnName, columnType);
    }
}
