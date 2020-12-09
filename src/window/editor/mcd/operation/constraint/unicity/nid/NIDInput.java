package window.editor.mcd.operation.constraint.unicity.nid;


import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.constraint.unicity.UnicityEditor;
import window.editor.mcd.operation.constraint.unicity.UnicityInput;

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
