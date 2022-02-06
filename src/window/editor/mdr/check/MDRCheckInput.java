package window.editor.mdr.check;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRCheckInput extends PanelInput {

    public MDRCheckInput(MDRCheckEditor MDRCheckEditor) {
        super(MDRCheckEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRCheckInputContent(this);
    }

}
