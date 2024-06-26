package org.example.model.tokens;

public interface TokenInterface {
    EnumToken getType();
    String getName();
    void actionBefore(QueryGenerator generator); //tutaj w action będzie np dodawanie dynamicznych tokenów do seta.
    String generateNow();
    String generateAfter();
    @Deprecated(since = "Usuwamy ASAP") //(forRemoval = true)
    boolean otherCanNested(EnumToken other);
}
