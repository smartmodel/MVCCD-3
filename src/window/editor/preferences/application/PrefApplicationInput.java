package window.editor.preferences.application;


import utilities.window.editor.PanelInput;

public class PrefApplicationInput extends PanelInput {

     public PrefApplicationInput(PrefApplicationEditor prefApplicationEditor) {
        super(prefApplicationEditor);
        super.setInputContent(new PrefApplicationInputContent(this));
    }


}
