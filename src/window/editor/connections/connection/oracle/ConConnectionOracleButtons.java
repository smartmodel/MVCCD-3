package window.editor.connections.connection.oracle;


import utilities.window.editor.PanelButtonsContent;
import window.editor.connections.connection.ConConnectionButtons;

public class ConConnectionOracleButtons extends ConConnectionButtons {


    public ConConnectionOracleButtons(ConConnectionOracleEditor conResourceOracleEditor) {
        super(conResourceOracleEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new ConConnectionOracleButtonsContent(this);
    }
}
