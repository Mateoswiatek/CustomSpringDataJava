package org.example.model.tokens;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class DynamicToken implements TokenInterface{
    String name;
    String nowGeneratedCode;
    String laterGeneratedCode;
    Set<EnumToken> availableNestings;

    //TODO, możemy użyć Controllera. bo w nim sa wszystkie encje bazy danych. tak aby generować tokeny.

    @Override
    public EnumToken getType() {
        return EnumToken.DYNAMIC_TOKEN;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void actionBefore(QueryGenerator generator) {}

    @Override
    public void actionAfter(QueryGenerator generator) {

    }

    @Override
    public String generateNow() {
        return nowGeneratedCode;
    }

    @Override
    public String generateAfter() {
        return laterGeneratedCode;
    }

    @Override
    public boolean otherCanNested(TokenInterface other) {
        return availableNestings.contains(other.getType());
    }
}
