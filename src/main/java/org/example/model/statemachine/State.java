package org.example.model.statemachine;

import lombok.Getter;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;

import java.util.Deque;
import java.util.LinkedList;

public abstract class State implements StateInterface {
    @Getter
    protected static final StringBuilder stringBuilder = new StringBuilder();
    //FromX dodaje kontekst tu będzie Deque list / setów, map dostępnych.
    protected static final Deque<TokenInterface> dequeTokensContext = new LinkedList<>();
//    protected QueryGenerator generator;

}
