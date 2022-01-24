package window.editor.connections.connector.postgreSQL;


import utilities.window.editor.PanelInputContent;
import window.editor.connections.connector.ConConnectorInput;

public class ConConnectorPostgreSQLInput extends ConConnectorInput {

    public ConConnectorPostgreSQLInput(ConConnectorPostgreSQLEditor conConnectorPostgreSQLEditor) {
        super(conConnectorPostgreSQLEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new ConConnectorPostgreSQLInputContent(this);
    }

}
