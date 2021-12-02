package console;

import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.PreferencesManager;
import resultat.ResultatElement;

/**
 * This class allow you to send messages to the final user.
 * ShowText() methods allow you to just send a message.
 * ShowMessage() methods allow you assLink send a message coming entity messages_xx.properties file.
 * This class is a Singleton.
 * @author steve.berberat
 *
 */

public class ViewManager {

    /**
     * Show a message to the user through the console.
     * Plusieurs lignes dans cette version
     * @param resultatElement
     */

    static public void printResultatElement(ResultatElement resultatElement){
        WarningLevelManager wlm = WarningLevelManager.instance();
        WarningLevel prefWarningLevel = PreferencesManager.instance().preferences().getWARNING_LEVEL();
        WarningLevel resultatWarningLevel = resultatElement.getLevel().getWarningLevel();
        if(resultatWarningLevel == null || wlm.oneIsAsImportantAsSecond(resultatWarningLevel,prefWarningLevel)){
            ConsoleManager consoleManager = MVCCDManager.instance().getConsoleManager();
            consoleManager.printMessage(resultatElement.getText());
        }
    }

    /**
     * Print assLink the console that a new text has been added to the log file.
     */
    //TODO-1 A voir si et/ou mettre un appel (STB)
    static public void informUserNewTextAddToFileLog(){
        if(LogsManager.isTextAddedToLog() ){
            String message = MessagesBuilder.getMessagesProperty("file.add.text", LogsManager.getlogFilePath());
            ConsoleManager consoleManager = MVCCDManager.instance().getConsoleManager();
            consoleManager.printMessage(message);
        }
    }


}
