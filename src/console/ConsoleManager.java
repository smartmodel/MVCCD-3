package console;

import main.MVCCDManager;
import main.window.console.WinConsoleContent;
import thread.ConsolePile;
import utilities.Trace;

public class ConsoleManager {

    //private ConsoleThread consoleThread ;

    public ConsoleManager(){
        /*
        ConsoleThread consoleThread = new ConsoleThread();
        this.consoleThread = consoleThread;
        consoleThread.run();
        */
    }

    private  WinConsoleContent winConsoleContent(){
        return  (WinConsoleContent) MVCCDManager.instance().getWinConsoleContent();
    }

    public  void printMessage (String message){
        //System.setOut(new PrintStream(new JTextAreaOutputStream(winConsoleContent().getTextArea())));
        //System.out.println(message);
        // L'impression se fait toujours après la fin du traitement invoqué...
        //winConsoleContent().getTextArea().append(message + System.lineSeparator());
        Trace.println(message);
        ConsolePile.add(message + System.lineSeparator());
        winConsoleContent().getConsoleThread().run();
        //consoleThread.run();
    }

    public  void clearMessages(){
        //winConsoleContent().getTextArea().removeAll();
        winConsoleContent().getTextArea().setText("");
    }

    /**
     * Utilisé pour envoyer la pile d'erreur sur la console de MVCCD si le fichier de log pose problème
     * @param e
     */
    public  void printStackTrace(Throwable e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i=0 ; i < stackTrace.length; i++){
            printMessage(stackTrace[i].toString());
        }
    }

    /*
    public ConsoleThread getConsoleThread() {
        return consoleThread;
    }

     */
}
