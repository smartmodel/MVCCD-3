package window.editor.mdr.mpdr.trigger;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MPDRTriggerButtons extends PanelButtons {


    public MPDRTriggerButtons(MPDRTriggerEditor MPDRTriggerEditor) {
        super(MPDRTriggerEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MPDRTriggerButtonsContent(this);
    }
}
