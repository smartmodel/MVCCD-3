package window.editor.preferences.application;


import newEditor.PanelButtons;

public class PrefApplicationButtons extends PanelButtons {

    public PrefApplicationButtons(PrefApplicationEditor prefApplicationEditor) {
        super(prefApplicationEditor);
        super.setButtonsContent(new PrefApplicationButtonsContent(this));

    }


}
