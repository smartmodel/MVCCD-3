package console;

import main.MVCCDManager;
import main.window.console.WinConsoleContent;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;

public class ConsoleManager {

    private IConsoleContentFrontEnd iConsoleContentFrontEnd = null;

    public ConsoleManager(){
    }

    private  WinConsoleContent winConsoleContent(){
        return  (WinConsoleContent) MVCCDManager.instance().getWinConsoleContent();
    }

    public  void printMessage (String message){
        //System.setOut(new PrintStream(new JTextAreaOutputStream(winConsoleContent().getTextArea())));
        //System.out.println(message);
        // L'impression se fait toujours après la fin du traitement invoqué...
        printMessage (message, winConsoleContent().getTextArea());
        if (iConsoleContentFrontEnd != null){
            printMessage (message, iConsoleContentFrontEnd.getTextArea());
        }
    }

    public  void printMessage (String message, JTextArea jTextArea){
        //System.setOut(new PrintStream(new JTextAreaOutputStream(winConsoleContent().getTextArea())));
        //System.out.println(message);
        // L'impression se fait toujours après la fin du traitement invoqué...

        if (StringUtils.isNotEmpty(jTextArea.getText())){
            jTextArea.append(System.lineSeparator());
        }
        jTextArea.append(message);
    }

    public  void clearMessages(){
        clearMessages(winConsoleContent().getTextArea());
        if (iConsoleContentFrontEnd != null){
            clearMessages(iConsoleContentFrontEnd.getTextArea());
        }
    }

    public  void clearMessages(JTextArea jTextArea){
        jTextArea.setText("");
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

    public IConsoleContentFrontEnd getiConsoleContentFrontEnd() {
        return iConsoleContentFrontEnd;
    }

    public void setiConsoleContentFrontEnd(IConsoleContentFrontEnd iConsoleContentFrontEnd) {
        this.iConsoleContentFrontEnd = iConsoleContentFrontEnd;
    }
}
