package console;


import main.MVCCDManager;
import org.apache.commons.lang.StringUtils;
import utilities.window.DialogMessage;

import java.awt.*;

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

/*
    static public void printNewResultat(ResultatElement resultatElement){
        ConsoleManager consoleManager = MVCCDManager.instance().getConsoleManager();
        consoleManager.clearMessages();
        LogsManager.newResultatElement(resultatElement);
        ViewManager.printResultatElement(resultatElement);
    }


 */

    static public void clear() {
        ConsoleManager consoleManager = MVCCDManager.instance().getConsoleManager();
        consoleManager.clearMessages();
        //TODO-0 A remplacer par simplement l'impression de la ligne de séparation
        //LogsManager.newResultatElement(new ResultatElement("", ResultatLevel.INFO));
        LogsManager.clear();
    }

    static public void printMessage(String message, WarningLevel warningLevel){
        LogsManager.printMessage(message);
        ViewManager.printMessage(message, warningLevel);
    }


    /**
     * Print a message to the user through the console or/and the log file.
     * Print only in one line.
     */

    /*
    static public void printContinueResultat(ResultatElement resultatElement){
        LogsManager.continueResultatElement(resultatElement);
        ViewManager.printResultatElement(resultatElement);
    }

     */

/*
    static public void printElementResultat(ResultatElement resultatElement){
        LogsManager.continueResultatElement(resultatElement);
        ViewManager.printResultatElement(resultatElement);
    }



    public static void printNewResultatWithMessage(String message, ResultatLevel resultatLevel) {
        Resultat resultat = new Resultat();
        resultat.setPrintImmediatelyForNewMessage(true);
        resultat.add(new ResultatElement(message, resultatLevel));
    }

 */


    /*
    public static void printResultat(Resultat resultat) {

            int i = 0;

            for (ResultatElement resultatElement : resultat.getElementsAllLevel()) {
                if (i == 0) {
                    ViewLogsManager.printNewResultat(resultatElement);
                } else {
                    ViewLogsManager.printContinueResultat(resultatElement);
                }
                i++;
            }
    }

     */


/*
    public static void dialogQuittance(Window window, Resultat resultat) {
        ArrayList<ResultatElement> elements = resultat.getElementsAllLevel();
        if (elements.size() > 0) {
            String message = elements.get(elements.size()-1).getText();
            if (resultat.isError()) {
                message = message + System.lineSeparator() +
                        MessagesBuilder.getMessagesProperty("dialog.error.console");
            }
            DialogMessage.showOk(window, message);
        }
    }

 */


    public static void dialogQuittance(Window window, String message) {
        DialogMessage.showOk(window, message);
    }

    /*
    public static void printMessageAndDialog(Window window, String message, ResultatLevel resultatLevel) {
        printNewResultatWithMessage(message, resultatLevel);
        DialogMessage.showOk(window, message);
    }

     */

    public static void printMessageAndDialog(Window window, String message, WarningLevel warningLevel) {
        printMessage(message, warningLevel);
        DialogMessage.showOk(window, message);
    }


    public static void catchException(Exception e, String message){

        //#MAJ 2022-03-04B Traitement d'erreur tant que MVC-CD n'est pas complètement chargé
        try {
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            if (StringUtils.isNotEmpty(e.getMessage())) {
                ViewLogsManager.printMessage(e.getMessage(), WarningLevel.INFO);
                System.out.println(e.getMessage());
            }
        } catch(Exception eViewLog) {
            // Pas d'info utilisateur tant que tout n'est pas chargé via ViewLogsManager.
            System.out.println(message);
        }
        System.out.println(e.toString());
        e.printStackTrace();
    }



    public static void catchException(Exception e, Window window, String message){
        catchException(e, message);
        dialogQuittance(window, message);
    }

    /*
    public static void catchException(Exception e, Window window, String message) {
        Resultat resultat = new Resultat();
        resultat.setPrintImmediatelyForResultat(true);
        resultat.addExceptionUnhandled(e);
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));
    }

     */
 }
