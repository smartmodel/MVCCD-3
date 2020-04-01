package window.editor.entity;


import newEditor.PanelButtons;

public class EntityButtons extends PanelButtons {


    public EntityButtons(EntityEditor entityEditor) {
        super(entityEditor);
        super.setButtonsContent (new EntityButtonsContent(this));
    }

}
