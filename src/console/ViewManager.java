package console;

import messages.MessagesBuilder;
import preferences.PreferencesManager;

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
     * @param text
     * @param warningLevel
     */
    static public void showTextLine(String text, WarningLevel warningLevel){
        boolean show = true;
        if(warningLevel != null){
            WarningLevel prefWarningLevel = PreferencesManager.instance().preferences().getWARNING_LEVEL();
            show = WarningLevelManager.instance().OneIsAsImportantAsSecond(warningLevel, prefWarningLevel);
        }
        if(show){
            Console.printMessage(text);
        }
    }


    /**
     * Show a message to the user through the console, using the root VP instruction.
     * @param text
     */
    static public void showTextLine(String text){
        showTextLine(text, null);
    }


    /**
     * Print assLink the console that a new text has been added to the log file.
     */
    static public void informUserNewTextAddToFileLog(){
//        if(LogsManager.isTextAddedToLog() && PreferencesService.toBoolean(Preferences.PRINT_LOGS)){
        if(LogsManager.isTextAddedToLog() ){
            String message = MessagesBuilder.getMessagesProperty("file.add.text", LogsManager.getlogFilePath());
            Console.printMessage(message);
        }
    }


}
