package utilities.window;

import messages.MessagesBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsContentMessagesPopupMenu extends JPopupMenu {
    public ButtonsContentMessagesPopupMenu() {
        init();
    }

    private void init() {
        final JMenuItem clear = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.messages.clear"));
        this.add(clear);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //
            }
        });
    }
}
