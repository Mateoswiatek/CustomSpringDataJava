package org.example.model.tokens;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class DynamicToken implements TokenInterface{
    String name;
    String nowGeneratedCode;
    String laterGeneratedCode;
    Set<EnumToken> availableNestings;

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
    public boolean otherCanNested(EnumToken other) {
        return availableNestings.contains(other);
    }
}
