package org.example.model.tokens;

public interface TokenInterface {
    EnumToken getType();
    String getName();
    void actionBefore(QueryGenerator generator); //tutaj w action będzie np dodawanie dynamicznych tokenów do seta.
    void actionAfter(QueryGenerator generator); //tu bedzie np usuwanie tokenów dynamicznych, bo wyszliśmy z zakresu
    String generateNow();
    String generateAfter();
    boolean otherCanNested(TokenInterface other);
}
