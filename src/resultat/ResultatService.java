package resultat;

import console.WarningLevel;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;

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

    public static void addExceptionUnhandled(Resultat resultat, Exception e) {
        String message = MessagesBuilder.getMessagesProperty("exception.unhandled");
        addExceptionInternal(resultat, e, message, ResultatLevel.EXCEPTION_UNHANDLED);
    }

    public static void addExceptionCatched(Resultat resultat, Exception e) {
        String message = MessagesBuilder.getMessagesProperty("exception.catched");
        addExceptionInternal(resultat, e, message, ResultatLevel.EXCEPTION_CATCHED);
    }

    private static void addExceptionInternal (Resultat resultat, Exception e, String message, ResultatLevel level) {
        resultat.add (new ResultatElement(message, level));
        resultat.add (new ResultatElement(e.toString(), level));
        if (StringUtils.isNotEmpty(e.getMessage())) {
            resultat.add (new ResultatElement(e.getMessage(), ResultatLevel.EXCEPTION_STACKTRACE));
        }
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i=0 ; i < stackTrace.length; i++){
            resultat.add (new ResultatElement(stackTrace[i].toString(), ResultatLevel.EXCEPTION_STACKTRACE));
        }
    }

    public static void finishTreatment(Resultat resultat,
                                       String propertyOk ,
                                       String propertyError ) {
        String message = "";
        if ( resultat.isNotError()){
            message = MessagesBuilder.getMessagesProperty(propertyOk);
        } else {
            message = MessagesBuilder.getMessagesProperty(propertyError);
        }
        resultat.add(new ResultatElement (message, ResultatLevel.INFO));
    }

}
