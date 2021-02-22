package window.editor.mcd.entity.mldr;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class EntMLDRInput extends PanelInput {

    public EntMLDRInput(EntMLDREditorBtn EntMLDREditorBtn) {
        super(EntMLDREditorBtn);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new EntMLDRInputContent(this);
    }

}
