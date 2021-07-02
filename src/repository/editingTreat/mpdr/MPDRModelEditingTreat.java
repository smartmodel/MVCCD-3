package repository.editingTreat.mpdr;

import generatesql.window.GenerateSQLWindow;
import java.awt.Window;
import main.MVCCDElement;
import mdr.MDRModel;
import mpdr.MPDRModel;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelEditor;

public class MPDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRModelEditor(owner, parent, (MDRModel) element, mode,
            new MPDRModelEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.physical";
    }

    public void treatGenerate(MVCCDElement mvccdElement) {
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
        new GenerateSQLWindow(mpdrModel);
    }
}
