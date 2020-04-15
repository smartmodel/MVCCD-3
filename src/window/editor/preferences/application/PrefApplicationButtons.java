package window.editor.preferences.application;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefApplicationButtons extends PanelButtons {

    public PrefApplicationButtons(PrefApplicationEditor prefApplicationEditor) {
        super(prefApplicationEditor);
        //super.setButtonsContent(new PrefApplicationButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new PrefApplicationButtonsContent(this);
    }
}
