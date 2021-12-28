package window.editor.preferences.project.mpdr.postgresql;


import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.mpdr.PrefMPDRInput;

public class PrefMPDRPostgreSQLInput extends PrefMPDRInput {

     public PrefMPDRPostgreSQLInput(PrefMPDRPostgreSQLEditor prefMPDRPostgreSQLEditor) {
        super(prefMPDRPostgreSQLEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMPDRPostgreSQLInputContent(this);
    }
}
