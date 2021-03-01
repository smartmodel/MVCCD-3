package window.editor.preferences.project.mpdr.postgresql;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMPDRPostgreSQLInput extends PanelInput {

     public PrefMPDRPostgreSQLInput(PrefMPDRPostgreSQLEditor prefMPDRPostgreSQLEditor) {
        super(prefMPDRPostgreSQLEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMPDRPostgreSQLInputContent(this);
    }
}
