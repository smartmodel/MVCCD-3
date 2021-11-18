package window.editor.connections.connector.oracle;

import connections.ConConnectionOracle;
import connections.ConConnectorOracle;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import window.editor.connections.connector.ConConnectorButtonsContent;

public class ConConnectorOracleButtonsContent extends ConConnectorButtonsContent {


    public ConConnectorOracleButtonsContent(ConConnectorOracleButtons conConnectorOracleButtons) {
        super(conConnectorOracleButtons);
    }

    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        ConConnectorOracle conConnectorOracle = MVCCDElementFactory.instance().createConConnectorOracle((ConConnectionOracle) parent);
        return conConnectorOracle;
    }

    @Override
    protected String getHelpFileName() {
        return null;
    }

}
