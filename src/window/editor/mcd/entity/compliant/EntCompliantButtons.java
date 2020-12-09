package window.editor.mcd.entity.compliant;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class EntCompliantButtons extends PanelButtons {


    public EntCompliantButtons(EntCompliantEditorBtn entCompliantEditorBtn) {
        super(entCompliantEditorBtn);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new EntCompliantButtonsContent(this);
    }
}
