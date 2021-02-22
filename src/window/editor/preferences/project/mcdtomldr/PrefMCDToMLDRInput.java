package window.editor.preferences.project.mcdtomldr;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMCDToMLDRInput extends PanelInput {

     public PrefMCDToMLDRInput(PrefMCDToMLDREditor PrefMCDToMLDREditor) {
        super(PrefMCDToMLDREditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
         return new PrefMCDToMLDRInputContent(this);
    }
}
