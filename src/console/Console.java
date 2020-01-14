package console;

import main.MVCCDManager;
import main.window.console.WinConsoleContent;

public class Console {

    public Console() {
    }

    public void printMessage (String message){
        winConsoleContent().getTextArea().append(message + System.lineSeparator());
    }

    private WinConsoleContent winConsoleContent(){
        return  (WinConsoleContent) MVCCDManager.instance().getWinConsoleContent();
    }
}
