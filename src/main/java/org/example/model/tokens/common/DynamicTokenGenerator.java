package org.example.model.tokens.common;

import org.example.model.adnotations.databasecreator.DatabaseField;
import org.example.model.adnotations.databasecreator.DatabaseTable;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamicTokenGenerator {

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
        if(!entity.isAnnotationPresent(DatabaseTable.class)) {
            throw new RuntimeException("Class " + entity.getName() + " is not annotated with @" + DatabaseTable.class.getSimpleName());
        }
        var annotation = (DatabaseTable) entity.getAnnotation(DatabaseTable.class);
        String tableName = annotation.tableName();

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
                    public boolean otherCanNested(TokenInterface other) {
                        return false;
                    }
                }).collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, _) -> x1)); // ppo nazwie, jeśli jest więcej, to pierwszego
                // FFF Collectors.groupingBy(TokenInterface::getName)
    }
}
