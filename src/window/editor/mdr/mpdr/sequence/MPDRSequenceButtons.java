package window.editor.mdr.mpdr.sequence;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MPDRSequenceButtons extends PanelButtons {


    public MPDRSequenceButtons(MPDRSequenceEditor MPDRSequenceEditor) {
        super(MPDRSequenceEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MPDRSequenceButtonsContent(this);
    }
}
