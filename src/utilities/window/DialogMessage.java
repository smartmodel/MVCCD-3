package utilities.window;

import messages.MessagesBuilder;

import javax.swing.*;
import java.awt.*;

public class DialogMessage {

    /*
    private static DialogMessage instance ;

    public static synchronized DialogMessage instance(){
        if(instance == null){
            instance = new DialogMessage();
        }
        return instance;
    }
*/
    public static void showError(Window owner, String message){
        String title = MessagesBuilder.getMessagesProperty("dialog.error");
        JOptionPane.showMessageDialog(owner, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorEditor(Window owner){
        String message = MessagesBuilder.getMessagesProperty("editor.check.datas.error");
        showError(owner, message);
    }

    public static void showOk(Window owner, String message){
        String title = MessagesBuilder.getMessagesProperty("dialog.information");
        JOptionPane.showMessageDialog(owner, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmYesNo(Window owner, String message) {
        String title = MessagesBuilder.getMessagesProperty("dialog.confirm");
        return JOptionPane.showConfirmDialog(owner, message, title, JOptionPane.YES_NO_OPTION);
    }

}
