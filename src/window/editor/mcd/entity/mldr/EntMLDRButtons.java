package window.editor.mcd.entity.mldr;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class EntMLDRButtons extends PanelButtons {


    public EntMLDRButtons(EntMLDREditorBtn EntMLDREditorBtn) {
        super(EntMLDREditorBtn);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new EntMLDRButtonsContent(this);
    }
}
