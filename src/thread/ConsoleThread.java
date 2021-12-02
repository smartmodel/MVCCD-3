package thread;

import main.MVCCDManager;
import main.window.console.WinConsoleContent;
import utilities.Trace;

public class ConsoleThread extends Thread{

    public void run(){
        Trace.println("Entré");
        ConsolePile.print();
        Trace.println("WinConsoleContent  entré");
        WinConsoleContent console = MVCCDManager.instance().getWinConsoleContent();
        Trace.println("getTextArea().getText()  " + console.getTextArea().getText());
        console.getTextArea().revalidate();
        console.getTextArea().repaint();
        console.revalidate();
        console.repaint();
        console.setVisible(true);
    }



}
