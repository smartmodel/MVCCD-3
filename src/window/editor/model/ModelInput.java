package window.editor.model;


import utilities.window.editor.PanelInput;

public class ModelInput extends PanelInput {

    public ModelInput(ModelEditor modelEditor) {
        super(modelEditor);
        super.setInputContent( new ModelInputContent(this));
    }


}
