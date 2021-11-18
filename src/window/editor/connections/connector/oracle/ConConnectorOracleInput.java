package window.editor.connections.connector.oracle;


import utilities.window.editor.PanelInputContent;
import window.editor.connections.connector.ConConnectorInput;

public class ConConnectorOracleInput extends ConConnectorInput {

    public ConConnectorOracleInput(ConConnectorOracleEditor conConnectorOracleEditor) {
        super(conConnectorOracleEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new ConConnectorOracleInputContent(this);
    }

}
