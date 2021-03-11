package window.editor.mdr.parameter;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDRParameterInput extends PanelInput {

    public MDRParameterInput(MDRParameterEditor MDRParameterEditor) {
        super(MDRParameterEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MDRParameterInputContent(this);
    }

}
