package window.editor.model;


import newEditor.PanelButtons;

public class ModelButtons extends PanelButtons {


    public ModelButtons(ModelEditor modelEditor) {
        super(modelEditor);
        super.setButtonsContent (new ModelButtonsContent(this));
    }

}
