package utilities;

import preferences.Preferences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexingName {

    private final int START_INDEX_1 = 1 ;
    private final int START_INDEX_2 = 2 ;

    private String patternIndexing ;
    Pattern pattern ;

    private final int NO_INDEX = -1 ;
    private final int MAX_INDEX = 99 ;

    public IndexingName(String patternIndexing) {
        this.patternIndexing = patternIndexing;
        pattern = Pattern.compile(Preferences.MDR_INDICE_REGEXPR);
    }

    public String extractRoot(String str){
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            String strWithIndex = matcher.group();
            return str.substring(0, str.length()- strWithIndex.length());
        } else {
            // str sans indexation
            return str;
        }
    }

    public int extractIndex(String str){
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            String strWithIndex = matcher.group();
            String strIndex = str.substring(str.length()- strWithIndex.length() - 1, strWithIndex.length());
            return (int) Integer.valueOf(strIndex);
        } else {
            // pas d'index
            return NO_INDEX;
        }
    }

    public int indexInBrothers(String str, ArrayList<String> brothers){

        ArrayList<Integer> indicesBrothers = new ArrayList<Integer>();
        String strRootStr = extractRoot(str);
        if ( strRootStr != null) {
            int i = -1;
            for (String brother : brothers) {
                i++;
                Matcher matcher = pattern.matcher(brother);
                if (matcher.find()) {
                    String brotherRootStr = extractRoot(brother);
                    if (brotherRootStr.equals(strRootStr)) {
                        int brotherIndex = extractIndex(brother);
                        indicesBrothers.add(brotherIndex);
                     }
                }
            }
        }

         return Indexing.indexInBrothers(indicesBrothers);


        /*
        String strRootStr = extractRoot(str);

        int indexBrothers[] ;
        indexBrothers = new int[MAX_INDEX];

        int replaceIndex = NO_INDEX;
        int maxIndex = startIndex;

        if ( strRootStr != null) {
            int i = -1;
            for (String brother : brothers) {
                i++;
                Matcher matcher = pattern.matcher(brother);
                if (matcher.find()) {
                    String brotherRootStr = extractRoot(brother);
                    if (brotherRootStr.equals(strRootStr)) {
                        int brotherRootIndex = extractIndex(brother);
                        indexBrothers[i] = brotherRootIndex;
                        maxIndex++;
                    }
                }
            }
            for (int k = startIndex ; k < maxIndex ; k++){
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
        }
        if (replaceIndex != NO_INDEX) {
            return replaceIndex;
        } else{
            return maxIndex;
        }

         */
    }
}
