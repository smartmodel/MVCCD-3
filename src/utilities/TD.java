package utilities;

import java.util.ArrayList;

public class TD {

    static public ArrayList<String> printResultats(
            String context,
            String[] conditions,
            boolean[] rules, String[] rules_actions) {

        ArrayList<String> resultat = new ArrayList<String>();

        resultat.add("MCD - Table de décision");
        resultat.add(context);

        resultat.add("------");

        if (conditions.length > 0 ) {
            for (int i=0 ; i < conditions.length ; i++ ) {
                resultat.add("Condition " + (i + 1) + "  :  " + conditions[i]);
            }
        }
        resultat.add("------");

        if (rules.length > 0 ) {
            for (int i=0 ; i < rules.length ; i++ ) {
                if (rules[i]) {
                    resultat.add("Règle valide :  " + (i + 1) );
                    resultat.add("------");
                    resultat.add("Action       :  " + rules_actions[i] );
                }
            }
        }

        resultat.add("------");

        return resultat;
    }

}
