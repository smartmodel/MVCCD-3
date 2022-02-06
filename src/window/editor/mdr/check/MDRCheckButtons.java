package window.editor.mdr.check;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRCheckButtons extends PanelButtons {


    public MDRCheckButtons(MDRCheckEditor MDRCheckEditor) {
        super(MDRCheckEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRCheckButtonsContent(this);
    }
}
