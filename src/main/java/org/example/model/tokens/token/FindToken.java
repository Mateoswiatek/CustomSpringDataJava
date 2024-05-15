package org.example.model.tokens.token;

import lombok.Data;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;
import org.example.model.tokens.common.DynamicTokenGenerator;

import java.util.Set;

//todo dorobić to, zmienić faktycznie na find tokena wygenerować w action before tak aby pobierało sobie nazwę klaski
// tak aby wiedziało co ma dać do FROM. dlatego w before, tak aby w sytuacji zagnieżdżeń, nie stracić tego?
// w tedy chyba actionAfter jest useless?
@Data
public class FindToken implements TokenInterface {
    private String name = "#####";
    private String generateNow = "(";
    private String generateAfter = ")";
    private Set<TokenInterface> availableNestings = Set.of(EnumToken.FIND);

    @Override
    public EnumToken getType() {
        return EnumToken.START_MARKER;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void actionBefore(QueryGenerator generator) {
        //Dodanie wszystkich dynamicznych tokenów z tej bazy danych.
        var tokens = DynamicTokenGenerator.getTokens(generator.getEntityClass());
        generator.getTokens().putAll(tokens);
    }

    @Override
    public void actionAfter(QueryGenerator generator) {

    }

    @Override
    public String generateNow() {
        return generateNow;
    }


    @Override
    public String generateAfter() {
        return generateAfter;
    }

    @Override
    public boolean otherCanNested(TokenInterface other) {
        return availableNestings.contains(other.getType());
    }
}
