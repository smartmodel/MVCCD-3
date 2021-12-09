package window.editor.connections.connection.oracle;

import connections.ConConnectionOracle;
import connections.ConnectionsOracle;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import window.editor.connections.connection.ConConnectionButtonsContent;

public class ConConnectionOracleButtonsContent extends ConConnectionButtonsContent {


    public ConConnectionOracleButtonsContent(ConConnectionOracleButtons conResourceOracleButtons) {
        super(conResourceOracleButtons);
    }

    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        ConConnectionOracle conResourceOracle = MVCCDElementFactory.instance().createConConnectionOracle((ConnectionsOracle) parent);
        return conResourceOracle;
    }

    @Override
    protected String getHelpFileName() {
        return null;
    }
}
