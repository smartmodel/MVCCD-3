package window.editor.connections.connector.postgreSQL;

import connections.ConDB;
import connections.postgresql.ConConnectionPostgreSQL;
import connections.postgresql.ConConnectorPostgreSQL;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.connections.connector.ConConnectorEditor;

import java.awt.*;

public class ConConnectorPostgreSQLEditor extends ConConnectorEditor {


    public ConConnectorPostgreSQLEditor(Window owner,
                                        ConConnectionPostgreSQL parent,
                                        ConConnectorPostgreSQL conConnectorPostgreSQL,
                                        String mode,
                                        EditingTreat editingTreat)  {
        super(owner, parent, conConnectorPostgreSQL, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new ConConnectorPostgreSQLButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new ConConnectorPostgreSQLInput(this);
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
