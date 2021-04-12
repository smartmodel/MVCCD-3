package resultat;

import java.util.ArrayList;

public class ResultatElement {
    String      text ;
    ResultatLevel    level ;

    public ResultatElement(String text, ResultatLevel level) {
        this.text = text;
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public ResultatLevel getLevel() {
        return level;
    }
}
