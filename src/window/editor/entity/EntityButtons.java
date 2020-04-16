package window.editor.entity;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class EntityButtons extends PanelButtons {


    public EntityButtons(EntityEditor entityEditor) {
        super(entityEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new EntityButtonsContent(this);
    }
}
