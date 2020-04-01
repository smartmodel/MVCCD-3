package window.editor.preferences.general;


import newEditor.PanelInput;

public class PrefGeneralInput extends PanelInput {

     public PrefGeneralInput(PrefGeneralEditor prefGeneralEditor) {
        super(prefGeneralEditor);
        super.setInputContent(new PrefGeneralInputContent(this));
    }


}
