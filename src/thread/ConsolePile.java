package thread;

import main.MVCCDManager;
import main.window.console.WinConsoleContent;
import utilities.Trace;

import java.util.ArrayList;

public class ConsolePile {

    private static ArrayList<String> messages = new ArrayList<String>();

    public static void add(String message){
        messages.add(message);
    }

    public static void print() {
        // Si des messages existent (Au départ il n'y en a pas lors de l'instanciation de l'application
        Trace.println("Entré ");
        if (messages.size() > 0){
            WinConsoleContent console = MVCCDManager.instance().getWinConsoleContent();
            Trace.println("Entrée de la boucle");
            while (messages.size() > 0) {
                String message = messages.get(0);
                Trace.println(message);
                messages.remove(0);
                console.getTextArea().append(message + System.lineSeparator());
                Trace.println("Apès affectation : " + console.getTextArea().getText());

            }
            //console.run();
            Trace.println("Sortie de la boucle");
        }
        Trace.println("Sorti");

    }

}
