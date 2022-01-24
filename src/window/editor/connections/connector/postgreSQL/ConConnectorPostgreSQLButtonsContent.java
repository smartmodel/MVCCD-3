package window.editor.connections.connector.postgreSQL;

import connections.postgresql.ConConnectionPostgreSQL;
import connections.postgresql.ConConnectorPostgreSQL;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import window.editor.connections.connector.ConConnectorButtonsContent;

public class ConConnectorPostgreSQLButtonsContent extends ConConnectorButtonsContent {


    public ConConnectorPostgreSQLButtonsContent(ConConnectorPostgreSQLButtons conConnectorPostgreSQLButtons) {
        super(conConnectorPostgreSQLButtons);
    }

    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        ConConnectorPostgreSQL conConnectorPostgreSQL =
                MVCCDElementFactory.instance().createConConnectorPostgreSQL((ConConnectionPostgreSQL) parent);
        return conConnectorPostgreSQL;
    }

    @Override
    protected String getHelpFileName() {
        return null;
    }

}
