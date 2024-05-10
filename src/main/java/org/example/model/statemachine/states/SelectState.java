package org.example.model.statemachine.states;

import org.example.model.statemachine.State;
import org.example.model.statemachine.StateInterface;
import org.example.model.tokens.TokenInterface;

public class SelectState extends State {
    private String generate = "SELECT ";


//    @Override
    public StateInterface generate() {
        stringBuilder.append(generate);
        return null;
    }

    @Override
    public void nextState(TokenInterface token) {

    }
}
