package org.example.model.tokens;

import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

@AllArgsConstructor
public class QueryGenerator {
    String tableName; // nazwa tabeli
    Type idType;

    public void processMethod(Method method) {

    }

    // Jakoś łądniej to robi
    public void processToken(TokenInterface token) {
        switch (token){
            case CreationTokens creationToken -> {

            }
            default -> throw new IllegalStateException("Unexpected value: " + token);
        }



    }
}
