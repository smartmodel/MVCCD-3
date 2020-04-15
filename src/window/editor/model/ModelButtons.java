package window.editor.model;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class ModelButtons extends PanelButtons {


    public ModelButtons(ModelEditor modelEditor) {
        super(modelEditor);
        //super.setButtonsContent (new ModelButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new ModelButtonsContent(this);
    }
}
