package window.editor.mdr.table;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRTableInput extends PanelInput {

    public MDRTableInput(MDRTableEditor MDRTableEditor) {
        super(MDRTableEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRTableInputContent(this);
    }

}
