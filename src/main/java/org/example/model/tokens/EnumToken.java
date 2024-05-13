package org.example.model.tokens;

import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public enum EnumToken implements TokenInterface {
    START("START", "(", ");"),
    FIND("find", "SELECT ", " FROM "),
    ALL("All", " * ", ""),

    //Jako flaga. aby mozna było zagnieżdżać zmienne.
    DYNAMIC_TOKEN("####", "", "");

    private final String name;
    private final String nowGeneratedCode;
    private final String laterGeneratedCode;
    private final Set<EnumToken> availableNestings = new HashSet<>(); // tak aby można było dodawać dunamiczne


    @Override
    public EnumToken getType() {
        return this;
    }

    @Override
    public String getName() {
        return name;
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
    public void actionBefore(QueryGenerator generator){}

    @Override
    public boolean otherCanNested(TokenInterface other) {
        return availableNestings.contains(other.getType());
    }
}
