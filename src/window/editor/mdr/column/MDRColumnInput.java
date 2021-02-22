package window.editor.mdr.column;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRColumnInput extends PanelInput {

    public MDRColumnInput(MDRColumnEditor MDRColumnEditor) {
        super(MDRColumnEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRColumnInputContent(this);
    }

}
