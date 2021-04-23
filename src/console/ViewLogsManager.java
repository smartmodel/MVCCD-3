package console;


import main.MVCCDWindow;
import messages.MessagesBuilder;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.window.DialogMessage;

import java.util.ArrayList;

/**
 * This class have to be used when you want to tell something to user right now.
 * If you want to declare a message to user but you want the plug-in to wait the
 * end of process before showing the message (this is the classic way), you must
 * not use this class, but use class "Messages". The message you send will be
 * written in log files or in VP console, depending of the WarningLevel you set.
 * It's a Singleton so it exist only one instance of its.
 *
 * @author Steve Berberat
 *
 */

public class ViewLogsManager {


    static private void newResultat(ResultatElement resultatElement){
        ConsoleManager.clearMessages();
        LogsManager.newResultatElement(resultatElement);
        ViewManager.printResultatElement(resultatElement);
    }


    /**
     * Print a message to the user through the console or/and the log file.
     * Print only in one line.
     * @param resultatElement
     */

    static private void continueResultat(ResultatElement resultatElement){
        LogsManager.continueResultatElement(resultatElement);
        ViewManager.printResultatElement(resultatElement);
    }

    public static void printMessage(String message, ResultatLevel resultatLevel) {
        Resultat resultat = new Resultat();
        resultat.add(new ResultatElement(message, resultatLevel));
        printResultat(resultat);
    }

    public static void printResultat(Resultat resultat) {
        int i = 0 ;
        for (ResultatElement resultatElement : resultat.getElementsAllLevel()){
            if (i == 0){
                ViewLogsManager.newResultat(resultatElement);
            }else {
                ViewLogsManager.continueResultat(resultatElement);
            }
            i++ ;
        }
    }

    public static void dialogQuittance(MVCCDWindow mvccdWindow, Resultat resultat) {
        ArrayList<ResultatElement> elements = resultat.getElementsAllLevel();
        if (elements.size() > 0) {
            String message = elements.get(elements.size()-1).getText();
            if (resultat.isError()) {
                message = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.error.console");
            }
            DialogMessage.showOk(mvccdWindow, message);
        }
    }

}
