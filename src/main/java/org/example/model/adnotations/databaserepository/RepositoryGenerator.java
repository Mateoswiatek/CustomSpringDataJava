package org.example.model.adnotations.databaserepository;

import org.example.model.adnotations.databasecreator.DatabaseTable;
import org.example.model.tokens.QueryGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RepositoryGenerator {

//    public static <T> T generateRepositoryImpl(Class<T> interfaceClass) throws IllegalAccessException, InstantiationException {
//        if (!interfaceClass.isAnnotationPresent(RepoAutoImpl.class)) {
//            throw new IllegalArgumentException("Interface must be annotated with @AutoImplement");
//        }
//        if (!interfaceClass.isInterface()) {
//            throw new IllegalArgumentException("Only interfaces can be implemented"); // bo klasy mają swoje implementacje
//            //TODO może abstrakcyjne by się udało??
//        }
//
//        return (T) java.lang.reflect.Proxy.newProxyInstance(
//                interfaceClass.getClassLoader(),
//                new Class<?>[]{interfaceClass},
//                (proxy, method, args) -> {
//                    // Jeśli metoda jest statyczna, nie możemy jej zaimplementować
//                    if (Modifier.isStatic(method.getModifiers())) {
//                        throw new UnsupportedOperationException("Cannot implement static method");
//                    }
//
//                    //TODO tutaj bedzie cale serce naszej aplikacji, generowanie odpowiednich zapytań SQL
//
//                    // Domyślna implementacja zwracająca null dla typów referencyjnych i 0 dla typów prymitywnych
//                    Class<?> returnType = method.getReturnType();
//                    if (returnType.isPrimitive()) {
//                        if (returnType == boolean.class) return false;
//                        if (returnType == char.class) return '\u0000';
//                        if (returnType == byte.class ||
//                                returnType == short.class ||
//                                returnType == int.class ||
//                                returnType == long.class ||
//                                returnType == float.class ||
//                                returnType == double.class) return 0;
//                    }
//                    if(returnType == Integer.class) return Integer.valueOf(100);
//                    if(returnType == String.class) return "Witam i o zdrowie Pytam!";
//
//                    return null;
//                });
//    }

    public static <T> void generateRepositoryImpToFile(Class<T> interfaceClass, String outputPathPrefix) throws IllegalAccessException, InstantiationException {
        if (!interfaceClass.isAnnotationPresent(RepoAutoImpl.class)) {
            throw new IllegalArgumentException("Musi byc @RepoAutoImpl");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("Tylko Interface mozemy nadpisac"); // bo klasy mają swoje implementacje
            //TODO może abstrakcyjne by się udało??
        }

        DatabaseTable databaseAnnotation = null;
        //Dostanie się do adnotacji Encji
        //TODO na streamy to zamienić
        Type[] genericInterfaces = interfaceClass.getGenericInterfaces(); // Typy interfaców które ta klasa implementuje
        for (Type genericInterface : genericInterfaces) { // Po wzytkich
            if(genericInterface instanceof ParameterizedType parameterizedType && parameterizedType.getRawType().equals(AbstractRepository.class)) { // Tylko te parametryczne, bo nasza jest parametryczna. i jeśli jest to nsza klasa
                System.out.println(parameterizedType);
    //                    System.out.println(Arrays.toString(parameterizedType.getActualTypeArguments()));
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments(); // Nasze parametry T oraz ID
    //                    System.out.println(actualTypeArguments[0]);
                Type entityType = actualTypeArguments[0];
                Type idType = actualTypeArguments[1];

                System.out.println(entityType);
                Class<?> entityClass = (Class<?>) entityType; // rutowanie typu na klasę
                databaseAnnotation = entityClass.getAnnotation(DatabaseTable.class);
                System.out.println(databaseAnnotation.tableName());

                // Konwertujemy nasz typ na Classe zwykłą, uzykujemy dostęp do adnotacji, z adnotacji bierzemy nazwę tabeli.
                // Następnie uzyskujemy dotęp do pól klasy, myClass.getDeclaredFields(); dla każdego pola sprawdzamy czy ma adnotację że jest to pola bazodanowe,
                // dla każdego pobieramy nazwę kolumny w bazie danych, i
                // Później robimy mapę, nazwa pola (nazwa w klasie tej encji) na odpowiednie nazwy pól w bazie danych. z adnotacji.
                // Mając to wszystko, .... kontynuacja w README...
            }
        }
        System.out.println(databaseAnnotation.tableName());

        String interfaceName = interfaceClass.getSimpleName();

        //TODO importy niezbednych rzeczy, importy interface adnotacje

        //bierzemy metody z interface bazowego.
        var a = Arrays.stream(interfaceClass.getInterfaces()).filter( x -> x.equals(AbstractRepository.class)).findFirst().orElseThrow();
        Method[] baseMethods = a.getDeclaredMethods();

        /*
        Dodatkowe w realu
        import org.hibernate.Session;
         Session session = HibernateUtil.getSessionFactory().openSession();

        // Twórz PreparedStatement i ustawiaj parametry
        String query = "SELECT DISTINCT ... WHERE x.lastname = ? AND x.firstname = ?";
        PreparedStatement preparedStatement = session.connection().prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setObject(2, someObject);

        // Wykonaj zapytanie i przetwarzaj wyniki
        // ...

        // Nie zapomnij zamknąć sesji po zakończeniu
        session.close();

        będzie setObject
         */

        StringBuilder implementationCode = new StringBuilder();
        implementationCode.append("public class ")
                .append(interfaceName)
                .append("Impl implements ")
                .append(interfaceName)
                .append(" {\n");

        QueryGenerator queryGenerator = new QueryGenerator(databaseAnnotation);

        Method[] interfaceMethods = interfaceClass.getDeclaredMethods();

        List<Method> allMethods = new ArrayList<>();
        Collections.addAll(allMethods, baseMethods);
        Collections.addAll(allMethods, interfaceMethods);

        for (Method method : allMethods) {
            //TODO będie więcej zabawy z kluczami i typami.
//            System.out.println(method.toString());
            implementationCode.append("    @Override\n")
                    .append("    public ")
                    //TODO jeśli jest object, to zwracamy Typ przekazywany generycznie - T
                    // Podobnie z ID
                    .append(method.getReturnType().getSimpleName())
                    .append(" ")
                    .append(method.getName())
                    .append("(");

//            method.get
//            method.getName();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                implementationCode.append(parameter.getType().getSimpleName()).append(" ").append(parameter.getName());
                if (i < parameters.length - 1) {
                    implementationCode.append(", ");
                }
            }

            implementationCode
                    .append(") {\n")
                    .append("        String query = ")
                    .append(queryGenerator.processMethod(method))
                    .append(";\n")

                    .append("        // Tutaj bedzie implementacja moze kiedys ")
                    .append(method.getName())
                    .append("\n")
                    .append("    }\n\n");
        }
        implementationCode.append("}");

        // Zapisz kod implementacji do pliku
        try (FileWriter writer = new FileWriter(outputPathPrefix + interfaceName + "Impl.java")) {
            writer.write(implementationCode.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

