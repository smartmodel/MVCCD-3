package utilities;

import main.MVCCDElement;

import java.util.ArrayList;
import java.util.Collections;

public class Indexing {


    private static final int MAX_INDEX = 99 ;


    public static int indexInSiblings(ArrayList<Integer> indexBrothers){

        ArrayList<Integer> sortedIndexBrothers = indexBrothers;
        Collections.sort(sortedIndexBrothers);
        int i = 0 ;
        for (Integer index : sortedIndexBrothers){
            i++;
            if (index > i ){
                // Un trou existe. Il sera réutilisé
                return i ;
            }
        }
        // Pas de trou. Nouvel élément en fin de liste
        return i + 1 ;
    }
}
