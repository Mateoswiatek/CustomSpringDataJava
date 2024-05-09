package org.example.model.adnotations;

import lombok.Getter;
import org.example.User;
import org.example.model.adnotations.databasecreator.DatabaseField;
import org.example.model.adnotations.databasecreator.DatabaseTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Controller {
    private static Controller instance;
    private final HashMap<Class<?>, String> typeMapper = new HashMap<>();

    private final Map<Class<?>, EntityProperties> myEntities = new HashMap<>();

    private Controller() {
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

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public boolean containsEntity(Class<?> myClass) {
        return myEntities.containsKey(myClass);
    }

    public EntityProperties getEntity(Class<?> myClass) {
        return myEntities.get(myClass);
    }

    public EntityProperties addEntity(Class<?> myClass) {
        EntityProperties entityProperties = createProperties(myClass);
        myEntities.put(myClass,entityProperties);
        return entityProperties;
    }

    public EntityProperties createProperties(Class<?> myClass) {
        EntityProperties entityProperties = new EntityProperties();
        DatabaseTable databaseTable = myClass.getAnnotation(DatabaseTable.class);

        entityProperties.setTableName(databaseTable.tableName().isEmpty() ? myClass.getSimpleName().toLowerCase() : databaseTable.tableName());
        var columns = entityProperties.getFieldToColumns();
        Arrays.stream(myClass.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(DatabaseField.class))
                .forEach(x -> {
                    DatabaseField databaseField = x.getAnnotation(DatabaseField.class);
                    String columnType = databaseField.columnType().isEmpty() ? typeMapper.get(x.getType()) : databaseField.columnType();
                    columns.put(x.getName(), new DatabaseColumn(databaseField.columnName(), columnType));
                });
//        System.out.println(columns.toString());
//        System.out.println(entityProperties);
        return entityProperties;
    }
}
