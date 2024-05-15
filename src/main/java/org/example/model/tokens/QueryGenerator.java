package org.example.model.tokens;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.tokens.token.FindToken;

import java.lang.reflect.Method;
import java.util.*;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryGenerator {
    private static final Logger log = Logger.getLogger(QueryGenerator.class.getName());

    String chars = null;
    Class entityClass;
    Map<String, TokenInterface> tokens = new HashMap<>();
    Stack<TokenInterface> openedTokens = new Stack<>();
    Map<String, Class<?>> myEntities;
    StringBuilder output = new StringBuilder();

    // bo nie przy każdym repo będzie potrzeba,dopiero. gdy będzie From - w tedy dopiero ładujemy,
    // i dodajemy nazwy klas do Tokenów.przy "wychodzeniu" z tokenu, bedziemy usuwać te wartości z tokens.
    // tak aby tlyko w obrębie danego podzapytania można było używać.
    public void setMyEntities(Map<String, Class<?>> myEntities) {
        if(myEntities == null) {
            this.myEntities = myEntities;
        }
    }

    public QueryGenerator(Class entityClass) {
        tokens.putAll(Arrays.stream(EnumToken.values()).collect(Collectors.toMap(TokenInterface::getName, x -> x, (x1, _) -> x1)));
        this.entityClass = entityClass;
    }


    public String processMethod(Method method) {
        chars = method.getName();
        //TODO ogarnąć, co lepiej, czy przetwarzac, czy tylko dodac, chyba lepiej przetworzyć. bo w tedy wygenerujemy i tez dodamy.

        // inicjalizacja startu
        FindToken startToken = new FindToken();

        openedTokens.push(startToken);
        startToken.actionBefore(this);
        output.append(startToken.generateNow());

//        tokens.entrySet().stream().forEach(x -> {
//            log.info(x.getKey());
//            log.info(x.getValue().generateNow());
//        });

        log.info("rozmiar to " + openedTokens.size());
        while(!chars.isEmpty()){
            TokenInterface token = this.getToken();
            this.processToken(token);
        }

        //Domykanie wszystkich otwartych tokenów
        while(!openedTokens.isEmpty()){
            var oToken = openedTokens.pop();
            oToken.actionAfter(this);
            output.append(oToken.generateAfter());

        }

        return output.append(";").toString();
//        return "SELECT * FROM TABLE NazwaTabeli123; // wygenerowano z nazwy=" + method.getName();
    }
    public TokenInterface getToken() {
        Set<String> keys = tokens.keySet();
        List<Character> prefix = new ArrayList<>(){
            @Override
            public String toString() {
                return this.stream().map(Object::toString).collect(Collectors.joining());
            }
        };

        for(int i =0; i<chars.length(); i++) {
            prefix.add(chars.charAt(i));

            // tylko te, które zaczynają się od prefixku.
            // findMyName oraz find, to weźmieny findMyName, bo aż do find będa 2 elementy. a gdy bedzie findM będzie już jeden,
            keys = keys.stream().filter(x -> x.startsWith(prefix.toString())).collect(Collectors.toSet());
            //TODO więcesz szczegolow przy nie rozpoznaniu!
            if(keys.isEmpty()) { throw new RuntimeException("nie rozpoznano znaku!"); }
            if(keys.size() == 1) {
                String key = keys.iterator().next();
                chars = chars.substring(key.length()); // usunięcie przetworzonego fragmentu.
                return tokens.get(key);
            }
        }
        throw new RuntimeException("cos nie tak poszło!");

        //TODO oooooooo!!!! na początku wgl generacji, czyli przy starcie, np jako pole statyczne, tego generatora,
        // dodajemy hasheta. String Token. String to nazwa tokenu, token to token.
        // gdy nie znajdujemy, to czekamy na dopaowanie do nazwy metody, jeśli jest dopasowanie, to
        // tworzymy token dla tej nazwy, i dodajemy go do tej mapy, tak aby w sytuacji kiedy ktoś będzie chciał skorzystsać to możemy to zrobić.
        // Albo wgl, każda encja będzie miała swój zbiór tokenów. gdzie będzie mapowanie nazwy pola na nazwę w bazie danych.
        // w tedy wystarcy znajomość encji i mamy już wzystko co potrzeba. nie mamy zabawy w dynamiczne programowanie. ale jet wydajniej i ładniej.
    }

    public void processToken(TokenInterface token) {
        token.actionBefore(this);
        output.append(token.generateNow());


//        log.info("element to " + openedTokens.peek());
//        log.info("rozmiar to " + openedTokens.size());
//        log.info(token.generateNow());

        // zamykanie wszystkich poprzednich, w których nowy nie może być

        while(!openedTokens.isEmpty() && !openedTokens.peek().otherCanNested(token)) {
            var openedToken = openedTokens.pop();
            openedToken.actionAfter(this);
            output.append(openedToken.generateAfter());
        }

        openedTokens.push(token);
        log.info("rozmiar to " + openedTokens.size());
        log.info("Dodalismy " + token.getName());
    }


    public String test(Method method) {
        return "SELECT * FROM TABLE NazwaTabeli123; // wygenerowano z nazwy=" + method.getName();
    }
}
