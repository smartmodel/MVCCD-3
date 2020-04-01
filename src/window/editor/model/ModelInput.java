package window.editor.model;


import newEditor.PanelInput;

public class ModelInput extends PanelInput {

    public ModelInput(ModelEditor modelEditor) {
        super(modelEditor);
        super.setInputContent( new ModelInputContent(this));
    }


}
