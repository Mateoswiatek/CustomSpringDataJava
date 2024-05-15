package org.example.model.tokens.token;

import lombok.AllArgsConstructor;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;

import java.util.Set;

//find, delete update?
@AllArgsConstructor
public class SqlToken implements TokenInterface {
    private EnumToken type;
    private String name;
    private String generateNow;
    private String generateAfter;
    private Set<EnumToken> availableNestings;

    @Override
    public EnumToken getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void actionBefore(QueryGenerator generator) {
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
    public boolean otherCanNested(EnumToken other) {
        return availableNestings.contains(other);
    }
}
