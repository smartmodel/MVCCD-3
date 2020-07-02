package console;

import main.MVCCDManager;
import main.window.console.WinConsoleContent;

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
}
