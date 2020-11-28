package utilities;

import preferences.PreferencesManager;

public class Trace {

    public static void println(String text){
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            System.out.println(text);
        }
    }

    public static void print(String text){
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            System.out.print(text);
        }
    }
}
