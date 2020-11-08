package window.editor.operation.constraint.unicity.unique;

import mcd.MCDContConstraints;
import mcd.MCDUnicity;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.operation.constraint.unicity.UnicityEditor;

import java.awt.*;

public class UniqueEditor extends UnicityEditor {


        public UniqueEditor(Window owner,
                            MCDContConstraints parent,
                            MCDUnicity mcdUnicity,
                            String mode,
                            EditingTreat editingTreat )  {
        super(owner, parent, mcdUnicity, mode, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {

            return new UniqueButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new UniqueInput(this);
    }





    @Override
    protected String getPropertyTitleNew() {
            return "editor.unique.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.unique.update";
    }
}
