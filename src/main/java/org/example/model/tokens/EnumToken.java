package org.example.model.tokens;

import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public enum EnumToken implements TokenInterface {
    //TODO dla tych co mają FROM, działaniem przed bedzie usunięcie ", " jesli istnieje. pobieramy przed ostatni znak, jesli to przecinek, to usuwamy go z buildera.
    // //       coś w tym stylu, ewentualnie z palca sprawdzić,  output.lastIndexOf(",");
    ALL("All", "* ", "",  Set.of(), _ -> {}, _ -> {}),
    FIND("find", "SELECT ", "FROM ", Set.of(EnumToken.ALL) ,_ -> {}, _ -> {}),

    //Jako flaga. aby mozna było zagnieżdżać zmienne np przy select,
    DYNAMIC_TOKEN("####", "", "", Set.of(), _ -> {}, _ -> {}),
    START_MARKER("####", "(", ");", Set.of(), _ -> {}, _ -> {});

    private final String name;
    private String nowGeneratedCode;
    private final String laterGeneratedCode;
    private final Set<EnumToken> availableNestings;
    private final ActionInterface actionBefore;
    private final ActionInterface actionAfter;

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
    public void actionBefore(QueryGenerator generator){
        actionBefore.action(generator);
    }

    @Override
    public void actionAfter(QueryGenerator generator) {
        actionAfter.action(generator);
    }

    @Override
    public boolean otherCanNested(TokenInterface other) {
//        System.out.println("mamy" + this.getType() + " i pytamy sie o " + other.getType());
//        System.out.println(availableNestings.contains(other.getType()));
        return availableNestings.contains(other.getType());
    }
}
