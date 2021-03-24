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

    private final int NO_INDEX = 1 ;
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
            String strIndex = str.substring(str.length()- strWithIndex.length() );
            return (int) Integer.valueOf(strIndex);
        } else {
            // pas d'index
            return NO_INDEX;
        }
    }

    public int indexInSiblings(String str, ArrayList<String> brothers){

        ArrayList<Integer> indexBrothers = new ArrayList<Integer>();
        String strRootStr = extractRoot(str);
        if ( strRootStr != null) {
            int i = -1;
            for (String brother : brothers) {
                i++;
                String brotherRootStr = extractRoot(brother);
                if (brotherRootStr.equals(strRootStr)) {
                    int brotherIndex = extractIndex(brother);
                    indexBrothers.add(brotherIndex);
                }
            }
        }
        return Indexing.indexInSiblings(indexBrothers);
    }
}
