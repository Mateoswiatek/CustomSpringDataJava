package org.example.adnotations;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Controller {
    private static Controller instance;

    private Map<Class<?>, EntityProperties> myEntities = new HashMap<>();

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public boolean containsEntity(Class<?> myClass) {
        return myEntities.containsKey(myClass);
    }
}
