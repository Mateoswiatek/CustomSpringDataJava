package org.example.model.tokens.token;

import lombok.Data;
import org.example.model.tokens.EnumToken;
import org.example.model.tokens.QueryGenerator;
import org.example.model.tokens.TokenInterface;
import org.example.model.tokens.common.TokenGenerator;

import java.util.Set;

//todo dorobić to, zmienić faktycznie na find tokena wygenerować w action before tak aby pobierało sobie nazwę klaski
// tak aby wiedziało co ma dać do FROM. dlatego w before, tak aby w sytuacji zagnieżdżeń, nie stracić tego?
// w tedy chyba actionAfter jest useless?
@Data
public class TableToken implements TokenInterface {
    private String name = "marker";
    private String generateNow = "";
    private String generateAfter = "FROM ";
    private String tableName;
    private Set<EnumToken> availableNestings = Set.of(EnumToken.ALL_ENTITY, EnumToken.FIND, EnumToken.COUNT, EnumToken.DYNAMIC_TOKEN);
    private QueryGenerator queryGenerator;

    @Override
    public EnumToken getType() {
        return EnumToken.TABLE_MARKER;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void actionBefore(QueryGenerator generator) {
        queryGenerator = generator;
    }


    @Override
    public String generateNow() {
        return generateNow;
    }


    @Override
    public String generateAfter() {
        // usuwanie ewenutalnego ostatniego przecinka.
        var builder = queryGenerator.getOutput();
        var length = queryGenerator.getOutput().length();
        if (length >= 2 && queryGenerator.getOutput().charAt(length - 2) == ',') {
            builder.deleteCharAt(length - 2);
        }


        return generateAfter + TokenGenerator.getDatabaseTableNameFromClassName(tableName);
    }

    @Override
    public boolean otherCanNested(EnumToken other) {
//        return true;
        return availableNestings.contains(other);
    }
}
