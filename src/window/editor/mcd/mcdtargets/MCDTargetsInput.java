package window.editor.mcd.mcdtargets;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MCDTargetsInput extends PanelInput {

    public MCDTargetsInput(MCDTargetsEditor MCDTargetsEditor) {
        super(MCDTargetsEditor);
        //super.setInputContent( new AttributesInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new MCDTargetsInputContent(this);
    }
}
