package window.editor.preferences.project.mdrformat;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMDRFormatInput extends PanelInput {

     public PrefMDRFormatInput(PrefMDRFormatEditor prefMDRFormatEditor) {
        super(prefMDRFormatEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMDRFormatInputContent(this);
    }
}
