package org.example.model.tokens;

import lombok.AllArgsConstructor;


import java.util.Set;

@AllArgsConstructor
public enum EnumToken {
    //TODO dla tych co mają FROM, działaniem przed bedzie usunięcie ", " jesli istnieje. pobieramy przed ostatni znak, jesli to przecinek, to usuwamy go z buildera.
    // //       coś w tym stylu, ewentualnie z palca sprawdzić,  output.lastIndexOf(",");
    ALL_ENTITY,
    FIND,
    DYNAMIC_TOKEN,
    START_MARKER,
    TABLE_MARKER,
    COUNT,
    NONE


//    private final String name;
//    private String nowGeneratedCode;
//    private final String laterGeneratedCode;
//    private final Set<EnumToken> availableNestings;
//    private final ActionInterface actionBefore;
//    private final ActionInterface actionAfter;
}
