package main;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import console.ViewLogsManager;
import messages.MessagesBuilder;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;

import javax.swing.*;
import java.io.Console;

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
