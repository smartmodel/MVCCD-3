package resultat;

import console.Console;
import console.ViewLogsManager;
import console.WarningLevel;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import utilities.window.DialogMessage;

import java.awt.*;
import java.util.ArrayList;

public class ResultatService {

    public static int getNbElementsByLevel(Resultat resultat, ResultatLevel resultatLevel) {
       return getElementsByLevel(resultat, resultatLevel).size();
     }


    public static ArrayList<ResultatElement> getElementsByLevel(Resultat resultat, ResultatLevel resultatLevel) {
        ArrayList<ResultatElement> newResultatElements = new ArrayList<ResultatElement>();
        for (ResultatElement resultatElement : resultat.getElementsAllLevel()){
            if (resultatElement.getLevel() == resultatLevel){
                newResultatElements.add(resultatElement);
            }
        }
        return newResultatElements ;
    }

    public static void addException(Resultat resultat, Exception e) {
        String message = MessagesBuilder.getMessagesProperty("exception.unhandled");
        resultat.add (new ResultatElement(message, ResultatLevel.EXCEPTION_JAVA));
        resultat.add (new ResultatElement(e.toString(), ResultatLevel.INFO));
        if (StringUtils.isNotEmpty(e.getMessage())) {
            resultat.add (new ResultatElement(e.getMessage(), ResultatLevel.INFO));
        }
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i=0 ; i < stackTrace.length; i++){
            resultat.add (new ResultatElement(stackTrace[i].toString(), ResultatLevel.INFO));
        }
    }

    public static void finishTransaction(Resultat resultat,
                                         String message,
                                         Window window,
                                         boolean onlyError) {
        boolean fatal = resultat.isWithElementFatal();
        boolean exceptionJava = resultat.isWithElementException();
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        ViewLogsManager.resultat(resultat, WarningLevel.WARNING);
        if ( window != null) {
            if ( resultat.isError()) {
                message = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.error.console");
            }
            if (onlyError){
                if (resultat.isError()){
                    DialogMessage.showOk(window, message);
                }
            } else {
                DialogMessage.showOk(window, message);
            }
        }

    }

    public static void startTransaction(Resultat resultat, String message) {
        resultat.add( new ResultatElement(message, ResultatLevel.INFO));
    }
}
