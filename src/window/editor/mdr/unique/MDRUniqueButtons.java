package window.editor.mdr.unique;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRUniqueButtons extends PanelButtons {


    public MDRUniqueButtons(MDRUniqueEditor MDRUniqueEditor) {
        super(MDRUniqueEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRUniqueButtonsContent(this);
    }
}
