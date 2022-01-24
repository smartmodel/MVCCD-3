package window.editor.connections.connection.postgresql;

import connections.ConDB;
import connections.postgresql.ConConnectionPostgreSQL;
import connections.postgresql.ConnectionsPostgreSQL;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.connections.connection.ConConnectionEditor;

import java.awt.*;

public class ConConnectionPostgreSQLEditor extends ConConnectionEditor {


    public ConConnectionPostgreSQLEditor(Window owner,
                                         ConnectionsPostgreSQL parent,
                                         ConConnectionPostgreSQL conResourcePostgreSQL,
                                         String mode,
                                         EditingTreat editingTreat)  {
        super(owner, parent, conResourcePostgreSQL, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new ConConnectionPostgreSQLButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new ConConnectionPostgreSQLInput(this);
    }

    @Override
    protected String getTitleSpecialized() {
        return ConDB.POSTGRESQL.getText();
    }


    @Override
    protected ConDB getConDb() {
        return ConDB.POSTGRESQL;
    }
}
