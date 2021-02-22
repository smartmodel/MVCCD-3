package window.editor.mdr.model;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRModelButtons extends PanelButtons {


    public MDRModelButtons(MDRModelEditor MDRModelEditor) {
        super(MDRModelEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRModelContent(this);
    }
}
