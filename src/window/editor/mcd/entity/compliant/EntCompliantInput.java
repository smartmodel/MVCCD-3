package window.editor.mcd.entity.compliant;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class EntCompliantInput extends PanelInput {

    public EntCompliantInput(EntCompliantEditorBtn entCompliantEditorBtn) {
        super(entCompliantEditorBtn);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new EntCompliantInputContent(this);
    }

}
