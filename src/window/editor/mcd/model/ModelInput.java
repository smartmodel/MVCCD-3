package window.editor.mcd.model;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class ModelInput extends PanelInput {

    public ModelInput(ModelEditor modelEditor) {
        super(modelEditor);
        //super.setInputContent( new ModelInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new ModelInputContent(this);
    }
}
