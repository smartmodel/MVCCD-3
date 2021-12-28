package window.editor.preferences.project.mpdr.mysql;


import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.mpdr.PrefMPDRInput;

public class PrefMPDRMySQLInput extends PrefMPDRInput {

     public PrefMPDRMySQLInput(PrefMPDRMySQLEditor prefMPDRMySQLEditor) {
        super(prefMPDRMySQLEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMPDRMySQLInputContent(this);
    }
}
