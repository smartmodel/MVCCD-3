package window.editor.connections.connection.oracle;


import utilities.window.editor.PanelInputContent;
import window.editor.connections.connection.ConConnectionInput;

public class ConConnectionOracleInput extends ConConnectionInput {

    public ConConnectionOracleInput(ConConnectionOracleEditor conResourceOracleEditor) {
        super(conResourceOracleEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new ConConnectionOracleInputContent(this);
    }

}
