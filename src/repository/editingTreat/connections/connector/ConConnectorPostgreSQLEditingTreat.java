package repository.editingTreat.connections.connector;

import connections.postgresql.ConConnectionPostgreSQL;
import connections.postgresql.ConConnectorPostgreSQL;
import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.connections.connector.postgreSQL.ConConnectorPostgreSQLEditor;
import window.editor.connections.connector.postgreSQL.ConConnectorPostgreSQLInputContent;

import java.awt.*;

public class ConConnectorPostgreSQLEditingTreat extends ConConnectorEditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ConConnectorPostgreSQLInputContent(element);
    }

    /**
     * Fournit à la classe ancêtre l'éditeur d'entité à utiliser. C'est cet éditeur qui s'affiche à l'utilisateur lors
     * de la création d'une nouvelle entité.
     */
    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ConConnectorPostgreSQLEditor(owner, (ConConnectionPostgreSQL) parent,
                (ConConnectorPostgreSQL) element, mode, new ConConnectorPostgreSQLEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.con.connector";
    }


}
