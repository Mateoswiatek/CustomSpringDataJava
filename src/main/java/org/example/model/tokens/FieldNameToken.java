package org.example.model.tokens;

public class FieldNameToken implements TokenInterface{

    @Override
    public TokenInterface getTokenFromString() {
        return this;
    }
    public String nameOfField() {
        return "field";
    }
}
