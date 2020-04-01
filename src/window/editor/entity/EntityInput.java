package window.editor.entity;


import newEditor.PanelInput;

public class EntityInput extends PanelInput {

    public EntityInput(EntityEditor entityEditor) {
        super(entityEditor);
        super.setInputContent( new EntityInputContent(this));
    }


}
