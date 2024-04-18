package org.example.adnotations.databasecreator;

import org.example.adnotations.Controller;
import org.example.adnotations.EntityProperties;

import java.lang.reflect.Field;
import java.util.Arrays;
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
    private EntityProperties entityProperties;

    public String createTable(Class<?> myClass) {
        Controller controller = Controller.getInstance();
        if(controller.containsEntity(myClass)) {return "XD";}
        entityProperties = new EntityProperties();

        if(!myClass.isAnnotationPresent(DatabaseTable.class)){ throw new RuntimeException("Klasa nie ma adnotacji"); }
        DatabaseTable databaseTable = myClass.getAnnotation(DatabaseTable.class);
        tableName = databaseTable.name().isEmpty() ? myClass.getSimpleName().toLowerCase() : databaseTable.name();
        entityProperties.setTableName(tableName);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ").append(tableName).append(" (\n");

        Arrays.stream(myClass.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(DatabaseField.class))
                .forEach(x -> stringBuilder.append(createTableColumn(x)));


        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("\n)");

        return stringBuilder.toString();
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
