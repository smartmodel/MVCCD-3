package window.editor.mdr.table;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRTableButtons extends PanelButtons {


    public MDRTableButtons(MDRTableEditor MDRTableEditor) {
        super(MDRTableEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRTableButtonsContent(this);
    }
}
