package utilities;

import preferences.PreferencesManager;

public class Trace {

    public static void println(String text) {
        print(text + System.lineSeparator());
    }

    public static void print(String text) {
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            System.out.print(new Trace().getCallMethod() + text);
        }
    }

    public static void printlnAlways(String text) {
        printAlways(text + System.lineSeparator());
    }

    public static void printAlways(String text) {
        System.out.print(new Trace().getCallMethod() + text);
    }

    public String getCallMethod() {
        String resultat = "";
        int i = 0 ;
        boolean thisClassReached = false ; // La classe Trace est trouvée dans le début de la pile
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()){
            String classNameInStack = Thread.currentThread().getStackTrace()[i].getClassName();
            thisClassReached = thisClassReached || classNameInStack.equals(this.getClass().getName());
            if ( thisClassReached &&  (!classNameInStack.equals(this.getClass().getName()))){
                String methodNameInStack = Thread.currentThread().getStackTrace()[i].getMethodName();
                int line = Thread.currentThread().getStackTrace()[i].getLineNumber();
                return classNameInStack + "." + methodNameInStack + "() " + line + " :  ";
            }
            i++;
        }
        return "Trace.getCallMethod() : Classe/méthode non trouvées";
    }
}


