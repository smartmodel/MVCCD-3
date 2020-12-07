package repository.editingTreat.mpdr;

import main.MVCCDElement;
import mdr.MDRModel;
import mldr.MLDRModel;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mdr.MDRTableEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelEditor;
import window.editor.model.ModelEditor;
import window.editor.model.ModelInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MPDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRModelEditor(owner, parent, (MDRModel) element, mode,
                new MDRTableEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.physical";
    }

    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {

        return new ArrayList<String>();
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        return new ArrayList<String>();
    }

}
