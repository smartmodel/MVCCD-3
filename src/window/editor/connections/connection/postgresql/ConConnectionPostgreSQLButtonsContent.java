package window.editor.connections.connection.postgresql;

import connections.postgresql.ConConnectionPostgreSQL;
import connections.postgresql.ConnectionsPostgreSQL;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import window.editor.connections.connection.ConConnectionButtonsContent;

public class ConConnectionPostgreSQLButtonsContent extends ConConnectionButtonsContent {


    public ConConnectionPostgreSQLButtonsContent(ConConnectionPostgreSQLButtons conResourcePostgreSQLButtons) {
        super(conResourcePostgreSQLButtons);
    }

    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        ConConnectionPostgreSQL conResourcePostgreSQL =
                MVCCDElementFactory.instance().createConConnectionPostgreSQL((ConnectionsPostgreSQL) parent);
        return conResourcePostgreSQL;
    }

    @Override
    protected String getHelpFileName() {
        return null;
    }
}
