package org.example.model.statemachine.states;

import org.example.model.adnotations.databasecreator.DatabaseField;
import org.example.model.statemachine.State;
import org.example.model.statemachine.StateInterface;
import org.example.model.tokens.FieldNameToken;
import org.example.model.tokens.TokenInterface;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StartState extends State {

    public StartState(Class entityClass) {
        var fields = entityClass.getDeclaredFields();
        List<TokenInterface> tokens;
        Arrays.stream(fields).map(x -> new FieldNameToken())


                .collect(Collectors
                .toMap(Field::getName,
                        field -> field.getAnnotation(DatabaseField.class).columnName()
        ));
    }

//    @Override
    public StateInterface generate() {
        return null;
    }

    @Override
    public void nextState(TokenInterface token) {
        switch(token) {
            case
        }
    }
}
