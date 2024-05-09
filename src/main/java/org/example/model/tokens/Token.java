package org.example.model.tokens;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum Token {
    FIND("find", true, "(SELECT ", " );", new ArrayList<>()),
    ALL("All", true, " * ", "", null );

    private final String name;
    private final boolean hasAttributes;
    private final String nowGeneratedCode;
    private final String laterGeneratedCode;
    private final List<Token> availableNestings;


}
