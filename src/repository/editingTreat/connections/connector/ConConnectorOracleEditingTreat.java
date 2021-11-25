package repository.editingTreat.connections.connector;

import connections.ConConnectionOracle;
import connections.ConConnectorOracle;
import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.connections.connector.oracle.ConConnectorOracleEditor;
import window.editor.connections.connector.oracle.ConConnectorOracleInputContent;

import java.awt.*;

public class ConConnectorOracleEditingTreat extends ConConnectorEditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ConConnectorOracleInputContent(element);
    }

    /**
     * Fournit à la classe ancêtre l'éditeur d'entité à utiliser. C'est cet éditeur qui s'affiche à l'utilisateur lors
     * de la création d'une nouvelle entité.
     */
    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ConConnectorOracleEditor(owner, (ConConnectionOracle) parent, (ConConnectorOracle) element, mode,
                new ConConnectorOracleEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.con.connector";
    }


}
