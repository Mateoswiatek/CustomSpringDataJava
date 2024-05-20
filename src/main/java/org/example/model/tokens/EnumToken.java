package org.example.model.tokens;

import lombok.AllArgsConstructor;


import java.util.Set;

@AllArgsConstructor
public enum EnumToken {
    //TODO dla tych co mają FROM, działaniem przed bedzie usunięcie ", " jesli istnieje. pobieramy przed ostatni znak, jesli to przecinek, to usuwamy go z buildera.
    // //       coś w tym stylu, ewentualnie z palca sprawdzić,  output.lastIndexOf(",");
    ALL_ENTITY("wszystkie rekordy *"),
    FIND(""),
    DYNAMIC_TOKEN("dynamicznie generowane tokeny"),
    START_MARKER("startowy"),
    TABLE_MARKER("przypisanie odpowiedniej tabeli"),
    COUNT("zliczanie"),
    UP("wyjście do góry"),
    WHERE("warunki logiczne"),
    NONE("nic");

    private String opis;
}
