package main;

import com.formdev.flatlaf.*;
import console.ViewLogsManager;
import messages.MessagesBuilder;

import javax.swing.*;

public class MVCCD  {

    /**
     * Démarre MVC-CD-3  
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            MVCCDManager.instance().start();
        } catch(Exception e){
            if (MVCCDManager.instance().getConsoleManager() != null) {
                String message = MessagesBuilder.getMessagesProperty("main.finish.error");
                ViewLogsManager.catchException(e, MVCCDManager.instance().getMvccdWindow(), message);
            } else {
                e.printStackTrace();
            }
        }
    }
}
