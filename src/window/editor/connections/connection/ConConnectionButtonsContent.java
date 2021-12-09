package window.editor.connections.connection;

import console.IConsoleContentFrontEnd;
import utilities.window.editor.PanelButtonsContent;

import javax.swing.*;

public abstract class ConConnectionButtonsContent extends PanelButtonsContent implements IConsoleContentFrontEnd {


    public ConConnectionButtonsContent(ConConnectionButtons conConnectionButtons) {
        super(conConnectionButtons);
    }


    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }


    @Override
    public JTextArea getTextArea() {
        return super.getMessages();
    }

}
