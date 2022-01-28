package utilities;

import exceptions.CodeApplException;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;

public class UtilDivers {

    public static final int NULL = 1;
    public static final int EMPTY = 2;

    public static String arrayStringToString(ArrayList<String> array, String separator) {
        String resultat = "";
        boolean first = true;
        if (array != null) {
            for (String element : array) {
                if (! first) {
                    resultat = resultat + separator;
                }
                resultat = resultat + element;
                first = false;
            }
        }
        return resultat;
    }

    public static ArrayList<String> getArrayListFromNamesStringTagged(String names,
                                                                      String tagBegin,
                                                                      String tagEnd,
                                                                      boolean withTags) {
        ArrayList<String> resultat = new ArrayList();
        if (!StringUtils.isEmpty(names)) {
            String[] parts = names.split(tagEnd);
            int i;
            for (i = 0; i < parts.length; i++) {
                // Vérification de la présence du marqueur de début
                if (parts[i].indexOf(tagBegin) == 0) {
                    // Il n'y a qu'un seul caractère pour le symbole de début de marquage!
                    String name = parts[i].substring(1);
                    if (name != null) {
                        if (withTags) {
                            name = tagBegin + name + tagEnd;
                        }
                        resultat.add(name);
                    }
                }
            }
        }
        return resultat;
    }

    public static String fromBooleanToString(Boolean exprBoolean, int showNull) {
        if (exprBoolean != null) {
            if (exprBoolean) {
                return MessagesBuilder.getMessagesProperty("boolean.to.string.true");
            } else {
                return MessagesBuilder.getMessagesProperty("boolean.to.string.false");
            }
        } else {
            if (showNull == NULL) {
                return MessagesBuilder.getMessagesProperty("boolean.to.string.null");
            }
            if (showNull == EMPTY) {
                return "";
            }
        }
        return null;
    }


    public static String fromIntegerToString(Integer exprInteger, int showNull) {
        if (exprInteger != null) {
            return String.valueOf(exprInteger.intValue());
        } else {
            if (showNull == NULL) {
                return MessagesBuilder.getMessagesProperty("boolean.to.integer.null");
            }
            if (showNull == EMPTY) {
                return "";
            }
        }
        return null;
    }

    public static String fromStringToString(String exprString, int showNull) {
        if (exprString != null) {
            return exprString;
        } else {
            return stringNull(showNull);
        }
    }

    public static String stringNull(int showNull) {
        if (showNull == NULL) {
            return MessagesBuilder.getMessagesProperty("string.to.integer.null");
        }
        if (showNull == EMPTY) {
            return "";
        }
        return null;
    }

    public static void putValueRowInTable(JTable table, int selectedRow, Object[] row) {
        if (row.length >= 1) {
            for (int i = 0; i < row.length; i++) {
                table.setValueAt(row[i], selectedRow, i);
            }
        }
    }

    public static String toNoFree(String str) {
        String noFree = "";
        if (StringUtils.isNotEmpty(str)) {
            noFree = StringUtils.remove(str, ' ');

            /*
            Remarque: Il semblerait qu'il y ait un soucis d'encodage du fichier source (UtilDivers.java)
            Ci-après les caractères unicode sont données au lieu du caractère directement, de façon à éviter les
            erreurs d'interprétation des caractères lors de la lecture du fichier si cela se fait avec un mauvais
            encodage.
            */
            noFree = noFree.replace('\u00e0', 'a'); //à
            noFree = noFree.replace('\u00e2', 'a'); //â
            noFree = noFree.replace('\u00e4', 'a'); //ä
            noFree = noFree.replace('\u00e7', 'c'); //ç
            noFree = noFree.replace('\u00e9', 'e'); //é
            noFree = noFree.replace('\u00e8', 'e'); //è
            noFree = noFree.replace('\u00ea', 'e'); //ê
            noFree = noFree.replace('\u00eb', 'e'); //ë
            noFree = noFree.replace('\u00f2', 'o'); //ò
            noFree = noFree.replace('\u00f6', 'o'); //ö
            noFree = noFree.replace('\u00f9', 'u'); //ù
            noFree = noFree.replace('\u00fb', 'u'); //û
            noFree = noFree.replace('\u00fc', 'u'); //ü
        }
        return noFree;
    }

    public static <T> boolean isNotEmpty(ArrayList<T> arrayList) {
        if (arrayList != null) {
            if (arrayList.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isEmpty(ArrayList<T> arrayList) {
        return !isNotEmpty(arrayList);
    }

    public boolean equals(String str1, String str2){
        boolean c1 = str1 == null;
        boolean c2 = str2 == null;

        if ( c1 && c2 ) {
            return true;
        }
        if ( c1 && !c2){
                return false;
        }
        if ( !c1 && c2){
            return false;
        }
        if ( !c1 && !c2){
            if ( str1.equals(str2)) {
                return true;
            }
            else {
                return false;
            }
        }
        throw new CodeApplException("str1:" + str1 +  "  -  str2:" + str2);
    }

    public static String toEscapedForRegex (String str){
        /*
        char BLANK = ' ';
        char DOT = '.';
        String ESCAPE = "\\\\";
        if (str != null){
            String resultat = "";
            char[] chars = str.toCharArray();
            for (int i = 0  ; i < chars.length ; i++){
                if (chars[i] == BLANK){
                    resultat += ESCAPE + BLANK;
                } else if (chars[i] == DOT){
                    resultat += ESCAPE + DOT;
                } else {
                    resultat += chars[i];
                }
            }

            return resultat;
        } else {
            return null;
        }

         */

        char SP = ' ';
        String UC_SP = "\\u0020";
        char DOT = '.';
        String UC_DOT = "\\u002E";
        char LT = '<';
        String UC_LT = "\\u003C";
        char GT = '>';
        String UC_GT = "\\u003E";
        char PIPE = '|';
        String UC_PIPE = "\\u007C";
        char LOB = '[';  // Left Opening Bracket
        String UC_LOB = "\\u005B";
        char RCB = ']'; // Right Closing Bracket
        String UC_RCB = "\\u005D";
        char MINUS = '-';
        String UC_MINUS = "\\u002D";
        if (str != null){
            String resultat = "";
            char[] chars = str.toCharArray();
            for (int i = 0  ; i < chars.length ; i++){
                if (chars[i] == SP){
                    resultat += UC_SP;
                } else if (chars[i] == DOT) {
                    resultat += UC_DOT;
                } else if (chars[i] == LT) {
                    resultat += UC_LT;
                } else if (chars[i] == GT){
                    resultat += UC_GT;
                } else if (chars[i] == PIPE){
                    resultat += UC_PIPE;
                } else if (chars[i] == LOB){
                    resultat += UC_LOB;
                } else if (chars[i] == RCB){
                    resultat += UC_RCB;
                } else if (chars[i] == MINUS){
                    resultat += UC_MINUS;
                } else {
                    resultat += chars[i];
                }
            }

            return resultat;
        } else {
            return null;
        }
    }

    public static String dateHourFormatted(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String partDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(localDate);

        LocalTime localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        String partTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(localTime);

        return partDate + " " + partTime ;
    }

    public static String hourFormatted(Date date){
        LocalTime localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        String partTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(localTime);

        return partTime ;
    }


    public static String replaceKeyValue(String code, String key, String value) {
        return code.replaceAll("\\{" + key + "}", value);
    }

}
