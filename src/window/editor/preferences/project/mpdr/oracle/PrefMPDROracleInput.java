package window.editor.preferences.project.mpdr.oracle;


import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.mpdr.PrefMPDRInput;

public class PrefMPDROracleInput extends PrefMPDRInput {

     public PrefMPDROracleInput(PrefMPDROracleEditor prefMPDROracleEditor) {
        super(prefMPDROracleEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
         return new PrefMPDROracleInputContent(this);
    }
}
