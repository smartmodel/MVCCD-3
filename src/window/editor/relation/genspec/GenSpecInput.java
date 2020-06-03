package window.editor.relation.genspec;

import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class GenSpecInput extends PanelInput {

    public GenSpecInput(GenSpecEditor genSpecEditor) {
        super(genSpecEditor);
        //super.setInputContent( new AssociationInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new GenSpecInputContent(this);
    }
}
