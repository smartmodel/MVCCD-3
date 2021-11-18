package repository.editingTreat.connections.connection;

import connections.ConConnectionOracle;
import connections.ConnectionsOracle;
import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.connections.connection.ConConnectionInputContent;
import window.editor.connections.connection.oracle.ConConnectionOracleEditor;

import java.awt.*;

public class ConConnectionOracleEditingTreat extends ConConnectionEditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ConConnectionInputContent(element);
    }

    /**
     * Fournit à la classe ancêtre l'éditeur d'entité à utiliser. C'est cet éditeur qui s'affiche à l'utilisateur lors
     * de la création d'une nouvelle entité.
     */
    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ConConnectionOracleEditor(owner, (ConnectionsOracle) parent, (ConConnectionOracle) element, mode,
                new ConConnectionOracleEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.con.connection";
    }


}
