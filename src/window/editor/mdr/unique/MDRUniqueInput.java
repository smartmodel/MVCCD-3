package window.editor.mdr.unique;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRUniqueInput extends PanelInput {

    public MDRUniqueInput(MDRUniqueEditor MDRUniqueEditor) {
        super(MDRUniqueEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRUniqueInputContent(this);
    }

}
