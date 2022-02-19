package window.editor.preferences.project.mldr;


import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.mdr.PrefMDRInput;

public class PrefMLDRInput extends PrefMDRInput {

     public PrefMLDRInput(PrefMLDREditor prefMLDREditor) {
        super(prefMLDREditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMLDRInputContent(this);
    }
}
