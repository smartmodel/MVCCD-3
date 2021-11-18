package window.editor.connections.connector;

import utilities.window.editor.PanelButtonsContent;

public abstract class ConConnectorButtonsContent extends PanelButtonsContent {


    public ConConnectorButtonsContent(ConConnectorButtons conConnectorButtons) {
        super(conConnectorButtons);
    }


    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }


}
