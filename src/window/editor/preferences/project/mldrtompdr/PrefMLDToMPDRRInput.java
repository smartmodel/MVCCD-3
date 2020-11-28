package window.editor.preferences.project.mldrtompdr;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMLDToMPDRRInput extends PanelInput {

     public PrefMLDToMPDRRInput(PrefMLDRToMPDREditor PrefMLDRToMPDREditor) {
        super(PrefMLDRToMPDREditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
         return new PrefMLDRToMPDRInputContent(this);
    }
}
