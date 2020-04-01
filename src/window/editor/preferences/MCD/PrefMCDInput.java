package window.editor.preferences.MCD;


import newEditor.PanelInput;

public class PrefMCDInput extends PanelInput {

     public PrefMCDInput(PrefMCDEditor prefMCDEditor) {
        super(prefMCDEditor);
        super.setInputContent(new PrefMCDInputContent(this));
    }


}
