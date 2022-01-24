package window.editor.connections.connector.postgreSQL;


import utilities.window.editor.PanelButtonsContent;
import window.editor.connections.connector.ConConnectorButtons;

public class ConConnectorPostgreSQLButtons extends ConConnectorButtons {


    public ConConnectorPostgreSQLButtons(ConConnectorPostgreSQLEditor conConnectorPostgreSQLEditor) {
        super(conConnectorPostgreSQLEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new ConConnectorPostgreSQLButtonsContent(this);
    }
}
