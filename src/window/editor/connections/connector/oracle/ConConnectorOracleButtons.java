package window.editor.connections.connector.oracle;


import utilities.window.editor.PanelButtonsContent;
import window.editor.connections.connector.ConConnectorButtons;

public class ConConnectorOracleButtons extends ConConnectorButtons {


    public ConConnectorOracleButtons(ConConnectorOracleEditor conConnectorOracleEditor) {
        super(conConnectorOracleEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new ConConnectorOracleButtonsContent(this);
    }
}
