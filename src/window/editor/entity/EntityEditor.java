package window.editor.entity;

import utilities.window.PanelBorderLayout;

public class EntityEditor extends PanelBorderLayout {

    private EntityEditorContent content ;
    private EntityWindow entityWindow;

    public EntityEditor(EntityWindow entityWindow) {
        super();
        this.entityWindow = entityWindow;
        content = new EntityEditorContent(this);
        super.setContent(content);
    }

    public EntityWindow getEntityWindow() {
        return entityWindow;
    }

}
