package org.example.model.tokens.common;

import org.example.model.adnotations.databasecreator.DatabaseField;
import org.example.model.adnotations.databasecreator.DatabaseTable;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;
import org.example.model.tokens.token.LogicConjunction;
import org.example.model.tokens.token.LogicOperationToken;
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
                    private String comma = "";

                    @Override
                    public EnumToken getType() {
                        return EnumToken.DYNAMIC_TOKEN;
                    }

                    @Override
                    public String getName() {
                        return x.getName();
                    }

                    @Override
                    public void actionBefore(QueryGenerator generator) {
                        var list = generator.getProcessedToken();
                        if(!list.isEmpty() && list.getFirst().getType().equals(EnumToken.DYNAMIC_TOKEN)) {
                            comma = ",";
                            //alternatywnie można zrobić to tak, ale to jest mniej estetyczne.
//                            generator.getOutput().append(", ");
                        }
                    }

                    @Override
                    public String generateNow() {
                       return "";
                    }

                    @Override
                    public String generateAfter() {
                        return comma + tableName + "." + x.getAnnotation(DatabaseField.class).columnName() + " ";
//                        return STR."\{comma}\{tableName}.\{x.getAnnotation(DatabaseField.class).columnName()} ";
                    }

                    @Override
                    public boolean otherCanNested(EnumToken other) {
                        return false;
                    }
                }).collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, _) -> x1)); // ppo nazwie, jeśli jest więcej, to pierwszego
                // FFF Collectors.groupingBy(TokenInterface::getName)
    }

    //TODO tutaj robimy dynamiczne , ewentualnie zrobić to jako signeton
    public static Set<String> getTableNames() {
        return Set.of("User", "Address");
    }

    public static Map<String, TokenInterface> getSqlTokens() {
        var list = List.of(
                new StartToken(),
                new SqlToken(EnumToken.UP, "UP", "", "", Set.of()),
                new SqlToken(EnumToken.FIND, "find", "SELECT ", "", Set.of()),
                new SqlToken(EnumToken.ALL_ENTITY, "All", "* ", "", Set.of()),
                new SqlToken(EnumToken.COUNT, "count", "SELECT COUNT( ", "), ", Set.of(EnumToken.ALL_ENTITY, EnumToken.DYNAMIC_TOKEN, EnumToken.UP)){
//                    @Override
//                    public void actionBefore(QueryGenerator queryGenerator) {
//
//                    }
//                    @Override
//                    public String generateAfter() {
//
//                    }

                },
                new SqlToken(EnumToken.WHERE, "Where", "WHERE ", " ", Set.of()) {
                    @Override
                    public void actionBefore(QueryGenerator queryGenerator) {
                        var list = queryGenerator.getProcessedToken();
                        if(!(list.isEmpty() || list.getFirst().getType().equals(EnumToken.TABLE_MARKER))) {
                            throw new RuntimeException("Błąd przetwarzania, where musi być za FROM table. Twój wygenerowane zapytanie: " + queryGenerator.getOutput().toString());
//                            throw new RuntimeException(STR."Błąd przetwarzania, where musi być za FROM table. Twój wygenerowane zapytanie: \{queryGenerator.getOutput().toString()}");
                        }
                    }
                }


        );



                                    /*
                            var builder = queryGenerator.getOutput();
        var length = queryGenerator.getOutput().length();
        if (length >= 2 && queryGenerator.getOutput().charAt(length - 2) == ',') {
            builder.deleteCharAt(length - 2);
        }
                     */
                // dodać to razem z jakimś UP które wychodzi. EnumToken.DYNAMIC_TOKEN

        //uzupełnić o operatory, nie zapominać o argumentach. wydzielić operatory logiczne?
        // zrobić, że tak naprawdę się zagnieżdżają logiczne opearatory, a logiczne operatory w sobie mogą zagnieżdżać DYNAMICe
        // where( (x = ?) and / or (y = ?) i takie coś mamy generować, może bez nawiasów, ale zawsze coś.

        return list.stream().collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, x2) -> x1));
    }

    //todo dodać alternatywnie Is5 / Is10 -> podawanie wartości??? w tedy trzeba by wygenerować inne tokeny np
    // liczbowe. Numeric, ale lepiej zostawić zminną
    public static Map<String, TokenInterface> getLogicOperationTokens() {
        var list = List.of(
            new LogicOperationToken("Is", "= ? ", "")
        );


        return list.stream().collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, x2) -> x1));
    }

    public static Map<String, TokenInterface> getLogicOperationTokena() {
        var list = List.of(
            new LogicConjunction("And", "AND ", ""),
            new LogicConjunction("Or", "OR ", "")
        );

        return list.stream().collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, x2) -> x1));
    }


}
