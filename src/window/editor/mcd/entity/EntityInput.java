package window.editor.mcd.entity;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class EntityInput extends PanelInput {

    public EntityInput(EntityEditor entityEditor) {
        super(entityEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new EntityInputContent(this);
    }

}
