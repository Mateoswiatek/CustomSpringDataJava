package org.example.model.tokens.token;

import lombok.Data;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;
import org.example.model.tokens.common.TokenGenerator;

import java.util.Set;

//todo
// ze stosu nazw tabel.
// jakies tokeny do tworzenia podzapytan będą miały w generate after usunięcie ostatniego elementu z tego stosu nazw tabel.
// i tez podzapytania mają generować sobie nawiasy. albo wgl, wydzielić StartToken też jako zwyły token, którego można uzywac do tworzenia
// podzapytań.
// w tedy w After jest zdejmowanie ze stosu nazwy tej tabeli.
// wgl, tworzymy sobie
// specjalny token który daje nam dostęp do nazw metod z wyższegp zapytania. jeśli np IdEqualsUpIdAnd to oznacza, że tabela_a.id=tabela_b.id
// w tedy, aby nie generować problemów, należy zrobić w tym Up, jako actionAfter, trzeba zrobić powrót do pierwotnej - czyli usunięcie tych tokenów.
// albo aby tak nie robić. zrobić Mapę gdzie kluczem będzie nazwa klasy, a wartościami będą mapy tokenów. cos w tym stylu

//4:22 nie pozdrawia XD

@Data
public class StartToken implements TokenInterface {
    private String name = "FROM";
    private String generateNow = "(";
    private String generateAfter = ") ";
    private Set<EnumToken> availableNestings = Set.of(EnumToken.FIND, EnumToken.TABLE_MARKER);

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
        var tokens = TokenGenerator.getTokens(generator.getEntityClass());
        generator.getTokens().putAll(tokens);
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
//        return availableNestings.contains(other);
        return true;
    }
}
