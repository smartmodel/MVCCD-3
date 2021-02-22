package window.editor.preferences.project.mpdr.oracle;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMPDROracleInput extends PanelInput {

     public PrefMPDROracleInput(PrefMPDROracleEditor prefMPDROracleEditor) {
        super(prefMPDROracleEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMPDROracleInputContent(this);
    }
}
