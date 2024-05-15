package org.example.model.tokens.common;

import org.example.model.adnotations.databasecreator.DatabaseField;
import org.example.model.adnotations.databasecreator.DatabaseTable;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;
import org.example.model.tokens.token.SqlToken;
import org.example.model.tokens.token.StartToken;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TokenGenerator {
    private static Reflections reflections = new Reflections("org.example");

    public static String getDatabaseTableNameFromClassName(final String className) {
        var classes = reflections.getTypesAnnotatedWith(DatabaseTable.class);
        return classes.stream().peek(x -> {
            System.out.println("nazwa to:");
            System.out.println(x.getSimpleName());
        })
                .filter(x -> x.getSimpleName().equals(className)).findFirst()
                .orElseThrow(() -> new RuntimeException("Nie znaleziono klasy o nazwie: #" + className + "#"))
                .getAnnotation(DatabaseTable.class).tableName();

//        try {
//            Class<?> klasa = Class.forName(className);
//            return getDatabaseTableNameFromClass(klasa);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Class not found: " + className);
//        }
    }

    public static String getDatabaseTableNameFromClass(Class entity) {
        if(!entity.isAnnotationPresent(DatabaseTable.class)) {
            throw new RuntimeException("Class " + entity.getName() + " is not annotated with @" + DatabaseTable.class.getSimpleName());
        }
        var annotation = (DatabaseTable) entity.getAnnotation(DatabaseTable.class);
        return  annotation.tableName();
    }

    public static Map<String, TokenInterface> getTokensFromClassName(final String className) {
        try {
            Class<?> klasa = Class.forName(className);
            return getTokens(klasa);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Class not found: " + className);
        }
    }

    public static Map<String, TokenInterface> getTokens(Class entity) {
        var fields = entity.getDeclaredFields();
        String tableName = getDatabaseTableNameFromClass(entity);

        return Arrays.stream(fields).filter(x -> x.isAnnotationPresent(DatabaseField.class))
                .map(x -> new TokenInterface(){
                    @Override
                    public EnumToken getType() {
                        return EnumToken.DYNAMIC_TOKEN;
                    }

                    @Override
                    public String getName() {
                        return x.getName();
                    }

                    @Override
                    public void actionBefore(QueryGenerator generator) {}

                    @Override
                    public void actionAfter(QueryGenerator generator) {}

                    @Override
                    public String generateNow() {
                        return tableName + "." + x.getAnnotation(DatabaseField.class).columnName();
                    }

                    @Override
                    public String generateAfter() {
                        return ", ";
                    }

                    @Override
                    public boolean otherCanNested(EnumToken other) {
                        return false;
                    }
                }).collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, x2) -> x1)); // ppo nazwie, jeśli jest więcej, to pierwszego
                // FFF Collectors.groupingBy(TokenInterface::getName)
    }

    //TODO tutaj robimy dynamiczne , ewentualnie zrobić to jako signeton
    public static Set<String> getTableNames() {
        return Set.of("User", "Event");
    }

    public static Map<String, TokenInterface> getSqlTokens() {
        var list = List.of(
                new StartToken(),
                new SqlToken(EnumToken.FIND, "find", "SELECT ", "", Set.of(EnumToken.DYNAMIC_TOKEN)),
                new SqlToken(EnumToken.ALL_ENTITY, "All", "* ", "", Set.of(EnumToken.DYNAMIC_TOKEN))

        );
        return list.stream().collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, x2) -> x1));
    }
}
