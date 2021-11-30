package repository.editingTreat.mpdr;

import generatesql.window.GenerateSQLWindow;
import generatorsql.viewer.SQLViewer;
import main.MVCCDElement;
import mpdr.MPDRModel;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.mpdr.model.MPDRModelEditor;

import java.awt.*;

public class MPDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MPDRModelEditor(owner, parent, (MPDRModel) element, mode,
            new MPDRModelEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.physical";
    }

    public void treatGenerateTB(MVCCDElement mvccdElement) {
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
        new GenerateSQLWindow(mpdrModel);
    }

    public void treatGenerate(MVCCDElement mvccdElement) {
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
        SQLViewer fen = new SQLViewer(mpdrModel);
        fen.setVisible(true);
    }
}
