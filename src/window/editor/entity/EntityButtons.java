package window.editor.entity;


import utilities.window.editor.PanelButtons;

public class EntityButtons extends PanelButtons {


    public EntityButtons(EntityEditor entityEditor) {
        super(entityEditor);
        super.setButtonsContent (new EntityButtonsContent(this));
    }

}
