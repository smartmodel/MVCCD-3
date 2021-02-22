package window.editor.mdr.model;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRModelInput extends PanelInput {

    public MDRModelInput(MDRModelEditor MDRModelEditor) {
        super(MDRModelEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRModelInputContent(this);
    }

}
