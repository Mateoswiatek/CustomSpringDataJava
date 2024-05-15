package org.example.model.tokens;

import lombok.AllArgsConstructor;


import java.util.Set;

@AllArgsConstructor
public enum EnumToken {
    //TODO dla tych co mają FROM, działaniem przed bedzie usunięcie ", " jesli istnieje. pobieramy przed ostatni znak, jesli to przecinek, to usuwamy go z buildera.
    // //       coś w tym stylu, ewentualnie z palca sprawdzić,  output.lastIndexOf(",");
    ALL_ENTITY("All", "* ", "",  Set.of(), x -> {}, x -> {}),


    FIND("XXXXXXXXXX", " ", "", Set.of() ,x -> {}, x -> {}),
    DYNAMIC_TOKEN("dynamic", "", "", Set.of(), x -> {}, x -> {}),
    START_MARKER("FROM", "(", ");", Set.of(), x -> {}, x -> {}),
    TABLE_MARKER("TABLE", "", "", Set.of(), x -> {}, x -> {});


    private final String name;
    private String nowGeneratedCode;
    private final String laterGeneratedCode;
    private final Set<EnumToken> availableNestings;
    private final ActionInterface actionBefore;
    private final ActionInterface actionAfter;
}
