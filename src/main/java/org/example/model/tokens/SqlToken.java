package org.example.model.tokens;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SqlToken implements TokenInterface{
    String now;
    String after;


    @Override
    public String generateNow() {
        return now;
    }

    @Override
    public String generateAfter() {
        return after;
    }
}
