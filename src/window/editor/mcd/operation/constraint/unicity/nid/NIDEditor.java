package window.editor.mcd.operation.constraint.unicity.nid;

import mcd.MCDContConstraints;
import mcd.MCDUnicity;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.mcd.operation.constraint.unicity.UnicityEditor;

import java.awt.*;

public class NIDEditor extends UnicityEditor {


        public NIDEditor(Window owner,
                         MCDContConstraints parent,
                         MCDUnicity mcdUnicity,
                         String mode,
                         EditingTreat editingTreat )  {
        super(owner, parent, mcdUnicity, mode, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new NIDButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new NIDInput(this);
    }




    @Override
    protected String getPropertyTitleNew() {
        return "editor.nid.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.nid.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.nid.read";
    }

}
