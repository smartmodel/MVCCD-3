package window.editor.operation.constraint.unique;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class UniqueInput extends PanelInput {

    public UniqueInput(UniqueEditor uniqueEditor) {
        super(uniqueEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new UniqueInputContent(this);
    }

}
