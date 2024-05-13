package org.example.model.tokens;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.Queue;
import java.util.logging.Logger;

@Data
@AllArgsConstructor
public class QueryGenerator {
    private static final Logger log = Logger.getLogger(QueryGenerator.class.getName());

    String chars; //todo jakoś ładniej to przetwarzac? tablica? ewentualnie lista charów?

    Class entityClass;
    List<TokenInterface> tokens = new ArrayList<>();
    Queue<TokenInterface> openedTokens = new LinkedList<>();
//    DatabaseTable databaseTable;


    public String processMethod(Method method) {
        method.getName();
        chars = method.getName();
        //TODO ogarnąć, co lepiej, czy przetwarzac, czy tylko dodac, chyba lepiej przetworzyć. bo w tedy wygenerujemy i tez dodamy.
        openedTokens.add(EnumToken.START);
        this.processToken(EnumToken.START);

        TokenInterface token = this.getToken(); // co będziemy robić?
        this.processToken(token);

//        var elements = this.splitNameToElementsList("długioStringaTegoTypu");
//        this.splitNameToTokens(elements);

        return "SELECT * FROM TABLE NazwaTabeli123; // wygenerowano z nazwy=" + method.getName();
    }
    public TokenInterface getToken() {
        StringBuilder prefix = new StringBuilder();
        for(int i =0; i<chars.length(); i++) {
            prefix.append(chars.charAt(i));

            //Z Notion opis, lecimy do momentu, kiedy nie będzie dokładnie jednego dopasowania. i w tedy je bierzemy,
            // i przesuwamy się o tyle ile uzupełniliśmy samemu. Dzięki takiemu podejściu, zawsze bierzemy najdłuższy element.
            // tak jak jest w teorii kompilacji
            // findMyName oraz find, to weźmieny findMyName, bo aż do find będa 2 elementy. a gdy bedzie findM będzie już jeden,
            // będzie uzupełnienie, a bardziej, weźmiemy ten token, i przesuniemy sie o tyle jaka jest róznica.



//            //TODO zamienić na Stringa / ew w case zamienić na Buildera
//            switch(prefix.toString()){
//                case "find" -> tokens.add(new SqlToken("SELECT ", "FROM " + entityClass.getSimpleName()));
//                //dodać sprawdzanie podzapytań. czyli najpierw czy jet specjalny znak,
//                // potem sprawdzamy nazwę
//                default -> {
//                    //sprawdzamy, czy ten prefix odpowiada nazwie nazych pól. jeśli tak, to zerujemy
//                    // prefix
//                }
//            }

        }


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

    public void processToken(TokenInterface token) {

        token.generateNow();


        switch (token) {
            case DynamicToken fieldNameToken -> {
//                fieldNameToken.nameOfField();

            }
            default -> throw new IllegalStateException("Unexpected value: " + token);
        }


    }


    public String test(Method method) {
        return "SELECT * FROM TABLE NazwaTabeli123; // wygenerowano z nazwy=" + method.getName();
    }
}
