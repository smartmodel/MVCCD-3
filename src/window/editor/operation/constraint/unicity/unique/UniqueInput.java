package window.editor.operation.constraint.unicity.unique;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;
import window.editor.operation.constraint.unicity.UnicityEditor;
import window.editor.operation.constraint.unicity.UnicityInput;
import window.editor.operation.constraint.unicity.UnicityInputContent;

public class UniqueInput extends UnicityInput {

    public UniqueInput(UnicityEditor unicityEditor) {
        super(unicityEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new UniqueInputContent(this);
    }

}
