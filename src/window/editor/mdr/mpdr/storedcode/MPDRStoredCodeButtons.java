package window.editor.mdr.mpdr.storedcode;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MPDRStoredCodeButtons extends PanelButtons {


    public MPDRStoredCodeButtons(MPDRStoredCodeEditor MPDRStoredCodeEditor) {
        super(MPDRStoredCodeEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MPDRStoredCodeButtonsContent(this);
    }
}
