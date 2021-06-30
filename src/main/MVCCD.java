package main;

import console.ViewLogsManager;
import messages.MessagesBuilder;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;

import java.io.Console;

public class MVCCD  {

    /**
     * Démarre MVC-CD-3  
     */
    public static void main(String[] args) {
        Console console;
        try {
            MVCCDManager.instance().start();
        } catch(Exception e){
            if (MVCCDManager.instance().getConsoleManager() != null) {
                String message = MessagesBuilder.getMessagesProperty("main.finish.error");
                ViewLogsManager.catchException(e, MVCCDManager.instance().getMvccdWindow(), message);
            } else {
                e.printStackTrace();
            }
        }
    }
}
