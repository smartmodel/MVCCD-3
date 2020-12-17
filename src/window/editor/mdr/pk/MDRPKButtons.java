package window.editor.mdr.pk;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRPKButtons extends PanelButtons {


    public MDRPKButtons(MDRPKEditor MDRPKEditor) {
        super(MDRPKEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRPKButtonsContent(this);
    }
}
