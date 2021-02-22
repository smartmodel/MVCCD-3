package window.editor.mcd.operation.constraint.unicity.unique;


import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.constraint.unicity.UnicityEditor;
import window.editor.mcd.operation.constraint.unicity.UnicityInput;

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
