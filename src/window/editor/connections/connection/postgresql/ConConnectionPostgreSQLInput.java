package window.editor.connections.connection.postgresql;


import utilities.window.editor.PanelInputContent;
import window.editor.connections.connection.ConConnectionInput;

public class ConConnectionPostgreSQLInput extends ConConnectionInput {

    public ConConnectionPostgreSQLInput(ConConnectionPostgreSQLEditor conResourceOracleEditor) {
        super(conResourceOracleEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new ConConnectionPostgreSQLInputContent(this);
    }

}
