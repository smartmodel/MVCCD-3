package window.editor.connections.connector;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDB;
import main.MVCCDManager;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;

import java.awt.*;

public abstract class ConConnectorEditor extends DialogEditor {


    public ConConnectorEditor(Window owner,
                              ConConnection parent,
                              ConConnector conConnector,
                              String mode,
                              EditingTreat editingTreat)  {
        super(owner, parent, conConnector, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

        //Console FrontEnd (Duplication de la console de la fenêtre principale
        MVCCDManager.instance().getConsoleManager().setiConsoleContentFrontEnd((ConConnectorButtonsContent) this.getButtons().getButtonsContent());

    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension (Preferences.CON_CONNECTOR_WINDOW_WIDTH, Preferences.CON_CONNECTOR_WINDOW_HEIGHT);
    }

    @Override
    protected void setSizeCustom(Dimension dimension) {
     }

    @Override
    protected Point getLocationCustom() {
        return null;

    }

    @Override
    protected void setLocationCustom(Point point) {
    }


    @Override
    protected String getPropertyTitleNew() {
        return "editor.con.connector.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.con.connector.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.con.connector.read";
    }

    protected abstract ConDB getConDb();


}
