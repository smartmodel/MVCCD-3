package window.editor.connections.connection.oracle;

import connections.ConConnectionOracle;
import connections.ConDB;
import connections.ConnectionsOracle;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.connections.connection.ConConnectionEditor;

import java.awt.*;

public class ConConnectionOracleEditor extends ConConnectionEditor {


    public ConConnectionOracleEditor(Window owner,
                                     ConnectionsOracle parent,
                                     ConConnectionOracle conResourceOracle,
                                     String mode,
                                     EditingTreat editingTreat)  {
        super(owner, parent, conResourceOracle, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new ConConnectionOracleButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new ConConnectionOracleInput(this);
    }

    @Override
    protected String getTitleSpecialized() {
        return ConDB.ORACLE.getText();
    }


    @Override
    protected ConDB getConDb() {
        return ConDB.ORACLE;
    }
}
