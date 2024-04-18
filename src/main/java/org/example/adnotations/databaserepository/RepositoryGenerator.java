package org.example.adnotations.databaserepository;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class RepositoryGenerator {

    public static <T> T generateRepositoryImpl(Class<T> interfaceClass) throws IllegalAccessException, InstantiationException {
        if (!interfaceClass.isAnnotationPresent(RepoAutoImpl.class)) {
            throw new IllegalArgumentException("Interface must be annotated with @AutoImplement");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("Only interfaces can be implemented"); // bo klasy mają swoje implementacje
            //TODO może abstrakcyjne by się udało??
        }

        return (T) java.lang.reflect.Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    // Jeśli metoda jest statyczna, nie możemy jej zaimplementować
                    if (Modifier.isStatic(method.getModifiers())) {
                        throw new UnsupportedOperationException("Cannot implement static method");
                    }

                    System.out.println(method.getName());

                    //TODO tutaj bedzie cale serce naszej aplikacji, generowanie odpowiednich zapytań SQL

                    // Domyślna implementacja zwracająca null dla typów referencyjnych i 0 dla typów prymitywnych
                    Class<?> returnType = method.getReturnType();
                    if (returnType.isPrimitive()) {
                        if (returnType == boolean.class) return false;
                        if (returnType == char.class) return '\u0000';
                        if (returnType == byte.class ||
                                returnType == short.class ||
                                returnType == int.class ||
                                returnType == long.class ||
                                returnType == float.class ||
                                returnType == double.class) return 0;
                    }
                    if(returnType == Integer.class) return Integer.valueOf(100);

                    return null;
                });
    }

    public static <T> void generateRepositoryImpToFilel(Class<T> interfaceClass, String outputPath) throws IllegalAccessException, InstantiationException {
        if (!interfaceClass.isAnnotationPresent(RepoAutoImpl.class)) {
            throw new IllegalArgumentException("Musi byc @RepoAutoImpl");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("Tylko Interface mozemy nadpisac"); // bo klasy mają swoje implementacje
            //TODO może abstrakcyjne by się udało??
        }

        String interfaceName = interfaceClass.getSimpleName();

        //TODO importy niezbednych rzeczy, importy interface adnotacje

        StringBuilder implementationCode = new StringBuilder();
        implementationCode.append("public class ")
                .append(interfaceName)
                .append("Impl implements ")
                .append(interfaceName)
                .append(" {\n");

        Method[] methods = interfaceClass.getDeclaredMethods();
        for (Method method : methods) {
            implementationCode.append("    @Override\n")
                    .append("    public ")
                    .append(method.getReturnType().getSimpleName())
                    .append(" ")
                    .append(method.getName())
                    .append("() {\n")
                    .append("        // Tutaj bedzie implementacja moze kiedys ")
                    .append(method.getName())
                    .append("\n")
                    .append("    }\n\n");
        }
        implementationCode.append("}");

        // Zapisz kod implementacji do pliku
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(implementationCode.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

