package window.editor.operation.constraint.unique;

import mcd.MCDContConstraints;
import mcd.MCDUnicity;
import mcd.MCDUnique;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.*;
import window.editor.model.ModelEditor;

import java.awt.*;

public class UniqueEditor extends DialogEditor {

    public static final int UNIQUE = 1 ;
    public static final int NID = 2 ;

        public UniqueEditor(Window owner,
                        MCDContConstraints parent,
                        MCDUnicity mcdUnicity,
                        String mode,
                        int scope,
                            EditingTreat editingTreat )  {
        super(owner, parent, mcdUnicity, mode, scope, editingTreat);

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
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.UNIQUE_WINDOW_WIDTH, Preferences.UNIQUE_WINDOW_HEIGHT);
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

        if (scope == UniqueEditor.UNIQUE) {
            return "editor.unique.new";
        }
        if (scope == UniqueEditor.NID) {
            return "editor.unique.new";
        }

        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        if (scope == UniqueEditor.UNIQUE) {
            return "editor.unique.update";
        }
        if (scope == UniqueEditor.NID) {
            return "editor.unique.update";
        }
        return null;
    }
}
