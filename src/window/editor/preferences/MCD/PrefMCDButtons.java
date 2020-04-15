package window.editor.preferences.MCD;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMCDButtons extends PanelButtons {

    public PrefMCDButtons(PrefMCDEditor prefMCDEditor) {
        super(prefMCDEditor);
        //super.setButtonsContent(new PrefMCDButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new PrefMCDButtonsContent(this);
    }
}
