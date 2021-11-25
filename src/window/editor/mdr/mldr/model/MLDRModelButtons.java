package window.editor.mdr.mldr.model;

import utilities.window.editor.PanelButtonsContent;
import window.editor.mdr.model.MDRModelButtons;

public class MLDRModelButtons extends MDRModelButtons {

    public MLDRModelButtons(MLDRModelEditor mldrModelEditor) {
        super(mldrModelEditor);
    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new MLDRModelButtonContent(this);
    }

}
