package resultat;

import java.util.ArrayList;

public class ResultatService {

    public static int getNbElementsByLevel(Resultat resultat, ResultatLevel resultatLevel) {
       return getElementsByLevel(resultat, resultatLevel).size();
        /*
        int nbElements = 0 ;
        for (ResultatElement resultatElement : resultat.getElements()){
            if (resultatElement.getLevel() == resultatLevel){
                nbElements = nbElements + 1 ;
            }
        }
        return nbElements;
        */
    }


    public static ArrayList<ResultatElement> getElementsByLevel(Resultat resultat, ResultatLevel resultatLevel) {
        ArrayList<ResultatElement> newResultatElements = new ArrayList<ResultatElement>();
        for (ResultatElement resultatElement : resultat.getElementsAllLevel()){
            if (resultatElement.getLevel() == resultatLevel){
                newResultatElements.add(resultatElement);
            }
        }
        return newResultatElements ;
    }

}
