package org.example;

import org.example.adnotations.databasecreator.DataBaseCreator;

import org.example.adnotations.databaserepository.RepositoryGenerator;

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


        try {
            MyInterfaceTest myInterface = RepositoryGenerator.generateRepositoryImpl(MyInterfaceTest.class);
            myInterface.method1(); // Wywołanie wygenerowanej implementacji
            System.out.println(myInterface.method2()); // Wywołanie wygenerowanej implementacji
            System.out.println(myInterface.method3());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        try {
            RepositoryGenerator.generateRepositoryImpToFilel(MyInterfaceTest.class, "MyInterfaceImpl.java");
            System.out.println("Udalo sie do MyInterfaceImpl.java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

