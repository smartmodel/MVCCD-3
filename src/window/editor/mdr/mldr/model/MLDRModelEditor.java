package window.editor.mdr.mldr.model;

import main.MVCCDElement;
import mldr.MLDRModel;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.mdr.model.MDRModelEditor;

import java.awt.*;

public class MLDRModelEditor extends MDRModelEditor {

    public MLDRModelEditor(Window owner, MVCCDElement parent, MLDRModel mldrModel, String mode, EditingTreat editingTreat) {
        super(owner, parent, mldrModel, mode, editingTreat);
    }


    @Override
    protected PanelButtons getButtonsCustom() {
        return new MLDRModelButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MLDRModelInput(this);
    }


    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.mldr.model.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.mldr.model.read";
    }

}
