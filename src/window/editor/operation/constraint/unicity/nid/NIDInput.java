package window.editor.operation.constraint.unicity.nid;


import utilities.window.editor.PanelInputContent;
import window.editor.operation.constraint.unicity.UnicityEditor;
import window.editor.operation.constraint.unicity.UnicityInput;
import window.editor.operation.constraint.unicity.UnicityInputContent;
import window.editor.operation.constraint.unicity.unique.UniqueInputContent;

public class NIDInput extends UnicityInput {

    public NIDInput(UnicityEditor unicityEditor) {
        super(unicityEditor);
        //super.setInputContent( new EntityInputContent(this));
    }

    @Override
    protected PanelInputContent createInputContentCustom() {

        return new NIDInputContent(this);
    }

}
