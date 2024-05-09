package org.example.model.tokens;

import lombok.AllArgsConstructor;
import org.example.model.adnotations.databasecreator.DatabaseTable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

@AllArgsConstructor
public class QueryGenerator {
    private static final Logger log = Logger.getLogger(QueryGenerator.class.getName());
    DatabaseTable databaseTable;

    public String processMethod(Method method) {
        // dzielenie nazwy na tokeny.
        method.getName();
        var elements = this.splitNameToElementsList("długioStringaTegoTypu");
        this.splitElementsToTokens(elements);

        return "SELECT * FROM TABLE NazwaTabeli123; // wygenerowano z nazwy=" + method.getName();
    }
    public void splitElementsToTokens(List<String> elements) {
        var token = new FieldNameToken();
        processToken(token);
        // tu dajemy to ze schematu.


        // robimy listę od googla tą ze wszystkich implementacjami danego interface. Loader czy jakoś tak.
        // robimy z tej listy hash seta, aby szybciej się szukało po nazwach???
        // jakieś zoptymalizowane szukanie po metodzie. i jak znajdziemy to zwracamy to

        //TODO oooooooo!!!! na początku wgl generacji, czyli przy starcie, np jako pole statyczne, tego generatora,
        // dodajemy hasheta. String Token. String to nazwa tokenu, token to token.
        // gdy nie znajdujemy, to czekamy na dopaowanie do nazwy metody, jeśli jest dopasowanie, to
        // tworzymy token dla tej nazwy, i dodajemy go do tej mapy, tak aby w sytuacji kiedy ktoś będzie chciał skorzystsać to możemy to zrobić.
        // Albo wgl, każda encja będzie miała swój zbiór tokenów. gdzie będzie mapowanie nazwy pola na nazwę w bazie danych.
        // w tedy wystarcy znajomość encji i mamy już wzystko co potrzeba. nie mamy zabawy w dynamiczne programowanie. ale jet wydajniej i ładniej.

    }

    public List<String> splitNameToElementsList(String methodName) {
        List<String> list = new ArrayList<>();
        int startIndex = 0;
        for (int i = 1; i < methodName.length(); i++) {
            if (Character.isUpperCase(methodName.charAt(i))) { // czy aktualny jest wielką
                list.add(methodName.substring(startIndex, i)); // dzielimy podciąg od startIndex do aktualnego indeksu i dodaj go do listy
                startIndex = i; // Zaktualizuj startIndex na aktualny indeks, aby rozpocząć nowy podciąg
            }
        }
        list.add(methodName.substring(startIndex)); // ostatni podciąg po ostatniej wielkiej literze
        log.info(list.toString());
        return list;
    }

    // Jakoś łądniej to robi
    public void processToken(TokenInterface token) {
        System.out.println(token.getClass());


        //to samo dla każdego tokenu

        switch (token) {
            case FieldNameToken fieldNameToken -> {
                fieldNameToken.nameOfField();

            }
            default -> throw new IllegalStateException("Unexpected value: " + token);
        }


    }


    public String test(Method method) {
        return "SELECT * FROM TABLE NazwaTabeli123; // wygenerowano z nazwy=" + method.getName();
    }
}
