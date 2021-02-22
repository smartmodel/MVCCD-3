package window.editor.mdr.pk;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRPKInput extends PanelInput {

    public MDRPKInput(MDRPKEditor MDRPKEditor) {
        super(MDRPKEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRPKInputContent(this);
    }

}
