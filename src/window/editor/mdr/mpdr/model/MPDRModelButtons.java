package window.editor.mdr.mpdr.model;

import utilities.window.editor.PanelButtonsContent;
import window.editor.mdr.model.MDRModelButtons;

public class MPDRModelButtons extends MDRModelButtons {

    public MPDRModelButtons(MPDRModelEditor mpdrModelEditor) {
        super(mpdrModelEditor);
    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new MPDRModelButtonContent(this);
    }

}
