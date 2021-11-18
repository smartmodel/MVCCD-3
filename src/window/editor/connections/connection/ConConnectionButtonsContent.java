package window.editor.connections.connection;

import utilities.window.editor.PanelButtonsContent;

public abstract class ConConnectionButtonsContent extends PanelButtonsContent {


    public ConConnectionButtonsContent(ConConnectionButtons conConnectionButtons) {
        super(conConnectionButtons);
    }


    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }


}
