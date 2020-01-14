package window.editor.entity;

import utilities.window.PanelBorderLayout;

public class EntityButtons extends PanelBorderLayout {

    private EntityButtonsContent content;
    private EntityWindow entityWindow;

    public EntityButtons(EntityWindow entityWindow) {
        super();
        this.entityWindow = entityWindow;
        super.setResizable(false);
        content = new EntityButtonsContent(this);
        super.setContent (content);
    }


    public EntityWindow getEntityWindow() {
        return entityWindow;
    }
}
