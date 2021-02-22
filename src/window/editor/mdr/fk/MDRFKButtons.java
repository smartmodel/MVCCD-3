package window.editor.mdr.fk;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRFKButtons extends PanelButtons {


    public MDRFKButtons(MDRFKEditor MDRFKEditor) {
        super(MDRFKEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRFKButtonsContent(this);
    }
}
