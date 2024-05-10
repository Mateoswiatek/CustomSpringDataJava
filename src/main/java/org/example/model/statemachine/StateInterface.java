package org.example.model.statemachine;

import org.example.model.tokens.TokenInterface;

public interface StateInterface {
//    StateInterface generate();
    void nextState(TokenInterface token);
}
