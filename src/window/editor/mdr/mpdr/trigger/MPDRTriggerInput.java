package window.editor.mdr.mpdr.trigger;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MPDRTriggerInput extends PanelInput {

    public MPDRTriggerInput(MPDRTriggerEditor MPDRTriggerEditor) {
        super(MPDRTriggerEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MPDRTriggerInputContent(this);
    }

}
