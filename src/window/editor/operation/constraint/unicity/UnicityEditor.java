package window.editor.operation.constraint.unicity;

import mcd.MCDContConstraints;
import mcd.MCDUnicity;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.*;

import java.awt.*;

public abstract class UnicityEditor extends DialogEditor {


        public UnicityEditor(Window owner,
                             MCDContConstraints parent,
                             MCDUnicity mcdUnicity,
                             String mode,
                             EditingTreat editingTreat )  {
        super(owner, parent, mcdUnicity, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }


    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.UNICITY_WINDOW_WIDTH, Preferences.UNIQUE_WINDOW_HEIGHT);
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
}
