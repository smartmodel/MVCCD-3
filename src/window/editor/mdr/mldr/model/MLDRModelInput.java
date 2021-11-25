package window.editor.mdr.mldr.model;

import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelInput;

public class MLDRModelInput extends MDRModelInput {

    public MLDRModelInput(MLDRModelEditor mldrModelEditor) {
        super(mldrModelEditor);
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MLDRModelInputContent(this);
    }

}
