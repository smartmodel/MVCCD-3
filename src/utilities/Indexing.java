package utilities;

import preferences.Preferences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Indexing {


    private static final int NO_INDEX = -1 ;
    private static final int MAX_INDEX = 99 ;


    public static int indexInBrothers(ArrayList<Integer> brothers){

        int indexBrothers[] ;
        indexBrothers = new int[MAX_INDEX];

        int replaceIndex = NO_INDEX;
        int maxIndex = 1;

        int i = -1;
        for (Integer brother : brothers) {
            i++;
            indexBrothers[i] = brother;
            maxIndex++;
        }

        // Parcours des indices frères
        for (int k = 0 ; k < i ; k++){
            boolean pit = true;
            for (int j = 0 ; j <= i ; j++){
                if (indexBrothers[j] == k ) {
                    pit = false;
                }
            }
            if (pit){
                replaceIndex = k ;
                break;
            }
        }
        if (replaceIndex != NO_INDEX) {
            return replaceIndex;
        } else{
            return maxIndex;
        }
    }
}
