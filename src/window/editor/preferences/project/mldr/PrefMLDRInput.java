package window.editor.preferences.project.mldr;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMLDRInput extends PanelInput {

     public PrefMLDRInput(PrefMLDREditor prefMLDREditor) {
        super(prefMLDREditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMLDRInputContent(this);
    }
}
