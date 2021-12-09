package window.editor.connections.connector;

import console.IConsoleContentFrontEnd;
import utilities.window.editor.PanelButtonsContent;

import javax.swing.*;

public abstract class ConConnectorButtonsContent extends PanelButtonsContent implements IConsoleContentFrontEnd {


    public ConConnectorButtonsContent(ConConnectorButtons conConnectorButtons) {
        super(conConnectorButtons);
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
