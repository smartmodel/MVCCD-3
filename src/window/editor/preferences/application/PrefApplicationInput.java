package window.editor.preferences.application;


import newEditor.PanelInput;

public class PrefApplicationInput extends PanelInput {

     public PrefApplicationInput(PrefApplicationEditor prefApplicationEditor) {
        super(prefApplicationEditor);
        super.setInputContent(new PrefApplicationInputContent(this));
    }


}
