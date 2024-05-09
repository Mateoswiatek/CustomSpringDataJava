package org.example;

import org.example.model.adnotations.databasecreator.DataBaseCreator;

import org.example.model.adnotations.databaserepository.RepositoryGenerator;

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

//
//        try {
//            UserRepositoryTest myInterface = RepositoryGenerator.generateRepositoryImpl(UserRepositoryTest.class);
//            myInterface.method1(); // Wywołanie wygenerowanej implementacji
//            System.out.println(myInterface.method2()); // Wywołanie wygenerowanej implementacji
//            System.out.println(myInterface.method3());
//        } catch (IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }

        try {
            RepositoryGenerator.generateRepositoryImpToFile(UserRepositoryTest.class, "./target/generated-sources/");
            System.out.println("Udalo sie do MyInterfaceImpl.java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

