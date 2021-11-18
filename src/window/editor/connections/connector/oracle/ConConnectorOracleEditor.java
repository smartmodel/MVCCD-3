package window.editor.connections.connector.oracle;

import connections.ConConnectionOracle;
import connections.ConConnectorOracle;
import connections.ConDB;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.connections.connector.ConConnectorEditor;

import java.awt.*;

public class ConConnectorOracleEditor extends ConConnectorEditor {


    public ConConnectorOracleEditor(Window owner,
                                    ConConnectionOracle parent,
                                    ConConnectorOracle conConnectorOracle,
                                    String mode,
                                    EditingTreat editingTreat)  {
        super(owner, parent, conConnectorOracle, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new ConConnectorOracleButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new ConConnectorOracleInput(this);
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
