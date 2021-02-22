package window.editor.preferences.project.mdr;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMDRInput extends PanelInput {

     public PrefMDRInput(PrefMDREditor prefMDREditor) {
        super(prefMDREditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMDRInputContent(this);
    }
}
