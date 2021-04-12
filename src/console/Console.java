package console;

import main.MVCCDManager;
import main.window.console.WinConsoleContent;
import utilities.Trace;

import java.util.ArrayList;

public class Console {

    private static WinConsoleContent winConsoleContent(){
        return  (WinConsoleContent) MVCCDManager.instance().getWinConsoleContent();
    }

    public static void printMessage (String message){
        winConsoleContent().getTextArea().append(message + System.lineSeparator());
    }

    public static void printMessages(ArrayList<String> messages) {
        for (String message : messages){
            printMessage(message);
        }
    }
    public static void clearMessages(){
        //winConsoleContent().getTextArea().removeAll();
        winConsoleContent().getTextArea().setText("");
    }

    /**
     * Utilisé pour envoyé la pile d'erreur sur la console de MVCCD si le fichier de log pose problème
     * @param e
     */
    public static void printStackTrace(Throwable e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i=0 ; i < stackTrace.length; i++){
            Console.printMessage(stackTrace[i].toString());
        }
    }
}
