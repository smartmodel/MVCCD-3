package utilities.files;

import preferences.Preferences;

public class UtilXML {


    public static String baliseBegin (String RichBalise){
        return  "<" + RichBalise + ">";
    }

    public static String baliseEnd (String balise){
        return  "</" + balise + ">";
    }

    public static String attributName(String name) {
        return Preferences.XML_ATTRIBUTE_NAME + "=\"" + name + "\"";
    }
}
