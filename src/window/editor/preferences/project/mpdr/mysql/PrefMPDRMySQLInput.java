package window.editor.preferences.project.mpdr.mysql;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMPDRMySQLInput extends PanelInput {

     public PrefMPDRMySQLInput(PrefMPDRMySQLEditor prefMPDRMySQLEditor) {
        super(prefMPDRMySQLEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMPDRMySQLInputContent(this);
    }
}
