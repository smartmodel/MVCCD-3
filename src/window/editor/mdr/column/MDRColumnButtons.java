package window.editor.mdr.column;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRColumnButtons extends PanelButtons {


    public MDRColumnButtons(MDRColumnEditor MDRColumnEditor) {
        super(MDRColumnEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRColumnButtonsContent(this);
    }
}
