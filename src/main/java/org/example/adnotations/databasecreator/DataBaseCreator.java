package org.example.adnotations.databasecreator;

import org.example.adnotations.Controller;

import org.example.adnotations.EntityProperties;
public class DataBaseCreator {

    public String createTable(Class<?> myClass) {
        if(!myClass.isAnnotationPresent(DatabaseTable.class)){ throw new RuntimeException("Klasa nie ma adnotacji"); }
        Controller controller = Controller.getInstance();
        EntityProperties entityProperties = controller.containsEntity(myClass) ? controller.getEntity(myClass) : controller.addEntity(myClass);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CREATE TABLE ").append(entityProperties.getTableName()).append(" (\n");

        stringBuilder.append(createTableColumn(entityProperties));
        stringBuilder.append(");\n");
        return stringBuilder.toString();
    }



    private String createTableColumn(EntityProperties entityProperties){
        StringBuilder stringBuilder = new StringBuilder();
        entityProperties.getFieldToColumns().values().forEach(field -> {
            stringBuilder
                    .append("    ").append(field.getColumnName()).append(" ").append(field.getColumnType()).append(",\n");
        });
        stringBuilder.deleteCharAt(stringBuilder.length()-2);
        return stringBuilder.toString();
    }

    public String addColumn(DatabaseField databaseField) {
        System.out.println("Column name: " + databaseField.columnName()
                + " | Clumn type: " + databaseField.columnType());

        String columnName = databaseField.columnName();
        String columnType = databaseField.columnType();
//        return String.format("ALTER TABLE %s ADD %s %s;\n", "tableNameXXXX", columnName, columnType);
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
