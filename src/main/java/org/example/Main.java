package org.example;

import org.example.adnotations.databasecreator.DataBaseCreator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User("Mateuesz", 23);

//        Field[] fields = user.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            if(field.isAnnotationPresent(DatabaseField.class)) {
//                DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
//                System.out.println("Field name: "+field.getName()
//                        + " | Column name: " + databaseField.columnName()
//                        + " | Clumn type: " + field.getType());
//            }
//        }

        DataBaseCreator creator = new DataBaseCreator();
        System.out.println(creator.createTable(User.class));
    }
}