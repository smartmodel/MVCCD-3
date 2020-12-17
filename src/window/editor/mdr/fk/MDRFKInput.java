package window.editor.mdr.fk;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRFKInput extends PanelInput {

    public MDRFKInput(MDRFKEditor MDRFKEditor) {
        super(MDRFKEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRFKInputContent(this);
    }

}
