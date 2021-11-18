package window.editor.connections.connection;

import connections.ConConnection;
import connections.ConDB;
import connections.ConnectionsDB;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;

import java.awt.*;

public abstract class ConConnectionEditor extends DialogEditor {


    public ConConnectionEditor(Window owner,
                               ConnectionsDB parent,
                               ConConnection conConnection,
                               String mode,
                               EditingTreat editingTreat)  {
        super(owner, parent, conConnection, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension (Preferences.CON_CONNECTION_WINDOW_WIDTH, Preferences.CON_CONNECTION_WINDOW_HEIGHT);
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
        return "editor.con.connection.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.con.connection.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.con.connection.read";
    }

    protected abstract ConDB getConDb();

}
