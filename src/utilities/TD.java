package utilities;

import console.WarningLevel;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;

import java.util.ArrayList;

public class TD {

    static public Resultat printResultats(
            String context,
            String[] conditions,
            boolean[] rules, String[] rules_actions) {

        ResultatElement separator = new ResultatElement("------------", ResultatLevel.INFO);
        Resultat resultat = new Resultat();

        resultat.add(new ResultatElement("MCD - Table de décision", ResultatLevel.INFO));
        resultat.add(new ResultatElement(context, ResultatLevel.INFO));

        resultat.add(separator);

        if (conditions.length > 0 ) {
            for (int i=0 ; i < conditions.length ; i++ ) {
                resultat.add(new ResultatElement("Condition " + (i + 1) + "  :  " + conditions[i],
                        ResultatLevel.INFO));
            }
        }
        resultat.add(separator);

        if (rules.length > 0 ) {
            for (int i=0 ; i < rules.length ; i++ ) {
                if (rules[i]) {
                    resultat.add(new ResultatElement("Règle valide :  " + (i + 1),
                            ResultatLevel.INFO) );
                    resultat.add(separator);
                    resultat.add(new ResultatElement("Action       :  " + rules_actions[i],
                            ResultatLevel.INFO) );
                }
            }
        }

        resultat.add(separator);

        return resultat;
    }

}
