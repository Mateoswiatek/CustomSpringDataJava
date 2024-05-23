package org.example.model.tokens.token;

import lombok.AllArgsConstructor;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;

import java.util.Set;

@AllArgsConstructor
public class LogicOperationToken implements TokenInterface {
    private String name;
    private String generateNow;
    private String generateAfter;


    @Override
    public EnumToken getType() {
        return EnumToken.LOGIC_OPERATION;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void actionBefore(QueryGenerator generator) {
        var set = Set.of(EnumToken.DYNAMIC_TOKEN);

        var list = generator.getProcessedToken();
        if(list.isEmpty() || !set.contains(list.getFirst().getType())) {
            throw new RuntimeException("Błąd, tylko wartości z tablic można 'sprawdzac' ty masz taki kod: " + generator.getOutput().toString());
        }
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
        System.out.println("przestarzala metoda, będzie update do nowych");
        return false;
    }
}
