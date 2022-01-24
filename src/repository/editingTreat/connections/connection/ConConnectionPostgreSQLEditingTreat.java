package repository.editingTreat.connections.connection;

import connections.postgresql.ConConnectionPostgreSQL;
import connections.postgresql.ConnectionsPostgreSQL;
import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.connections.connection.postgresql.ConConnectionPostgreSQLEditor;
import window.editor.connections.connection.postgresql.ConConnectionPostgreSQLInputContent;

import java.awt.*;

public class ConConnectionPostgreSQLEditingTreat extends ConConnectionEditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ConConnectionPostgreSQLInputContent(element);
    }

    /**
     * Fournit à la classe ancêtre l'éditeur d'entité à utiliser. C'est cet éditeur qui s'affiche à l'utilisateur lors
     * de la création d'une nouvelle entité.
     */
    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ConConnectionPostgreSQLEditor(owner, (ConnectionsPostgreSQL) parent, (ConConnectionPostgreSQL) element, mode,
                new ConConnectionPostgreSQLEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.con.connection";
    }


}
