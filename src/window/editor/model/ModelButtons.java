package window.editor.model;


import utilities.window.editor.PanelButtons;

public class ModelButtons extends PanelButtons {


    public ModelButtons(ModelEditor modelEditor) {
        super(modelEditor);
        super.setButtonsContent (new ModelButtonsContent(this));
    }

}
