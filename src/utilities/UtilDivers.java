package utilities;

import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.ArrayList;

public class UtilDivers {

    public static final int NULL = 1 ;
    public static final int EMPTY = 2 ;

    public static String ArrayStringToString(ArrayList<String> array, String separator){
        String resultat = "";
        boolean first = true;
        if (array != null) {
            for (String element : array) {
                if (first) {
                    resultat = resultat + separator;
                    first = false;
                }
                resultat = resultat + element;

            }
        }
        return resultat;
    }

    public static ArrayList<String> getArrayListFromNamesStringTagged(String names,
                                                                        String tagBegin,
                                                                        String tagEnd,
                                                                        boolean withTags){
        ArrayList<String> resultat = new ArrayList();
        if (!StringUtils.isEmpty(names)){
            String[] parts = names.split(tagEnd);
            int i;
            for (i=0; i<parts.length;i++){
                // Vérification de la présence du marqueur de début
                if (parts[i].indexOf(tagBegin) == 0){
                    // Il n'y a qu'un seul caractère pour le symbole de début de marquage!
                    String name = parts[i].substring(1);
                    if (name != null){
                        if (withTags){
                            name = tagBegin + name + tagEnd;
                        }
                        resultat.add(name);
                    }
                }
            }
        }
        return resultat;
    }

    public static String fromBooleanToString (Boolean exprBoolean, int showNull){
        if (exprBoolean != null){
            if (exprBoolean){
                return MessagesBuilder.getMessagesProperty("boolean.to.string.true");
            } else {
                return MessagesBuilder.getMessagesProperty("boolean.to.string.false");
            }
        } else {
            if ( showNull == NULL) {
                return MessagesBuilder.getMessagesProperty("boolean.to.string.null");
            }
            if ( showNull == EMPTY) {
                return "";
            }
        }
        return null;
    }


    public static String fromIntegerToString (Integer exprInteger, int showNull){
        if (exprInteger != null){
            return String.valueOf(exprInteger.intValue());
        } else {
            if ( showNull == NULL) {
                return MessagesBuilder.getMessagesProperty("boolean.to.integer.null");
            }
            if ( showNull == EMPTY) {
                return "";
            }
        }
        return null;
    }

    public static String fromStringToString (String exprString, int showNull){
        if (exprString != null){
            return exprString;
        } else {
            return stringNull(showNull);
        }
    }

    public static String stringNull(int showNull){
        if ( showNull == NULL) {
            return MessagesBuilder.getMessagesProperty("string.to.integer.null");
        }
        if ( showNull == EMPTY) {
            return "";
        }
        return null;
    }

    public static void putValueRowInTable(JTable table, int selectedRow, Object[] row) {
        if (row.length >= 1) {
            for (int i = 0; i < row.length - 1; i++) {
                table.setValueAt(row[i], selectedRow, i);
            }
        }
    }

    public static String toNoFree(String str){
        String noFree = "";
        if (StringUtils.isNotEmpty(str)) {
            noFree = StringUtils.remove(str, ' ');

            noFree = noFree.replace('à', 'a');
            noFree = noFree.replace('â', 'a');
            noFree = noFree.replace('ä', 'a');
            noFree = noFree.replace('ç', 'c');
            noFree = noFree.replace('é', 'e');
            noFree = noFree.replace('è', 'e');
            noFree = noFree.replace('ê', 'e');
            noFree = noFree.replace('ë', 'e');
            noFree = noFree.replace('ò', 'o');
            noFree = noFree.replace('ö', 'o');
            noFree = noFree.replace('ù', 'u');
            noFree = noFree.replace('û', 'u');
            noFree = noFree.replace('ü', 'u');
        }
        return noFree;
    }
}
