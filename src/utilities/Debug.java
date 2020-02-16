package utilities;

import preferences.PreferencesManager;

public class Debug {

    public static void println(String text){
        if (PreferencesManager.instance().preferences().getDEBUG()) {
            System.out.println(text);
        }
    }

    public static void print(String text){
        if (PreferencesManager.instance().preferences().getDEBUG()) {
            System.out.print(text);
        }
    }
}
