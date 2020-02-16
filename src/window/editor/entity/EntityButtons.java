package window.editor.entity;

import utilities.window.PanelBorderLayout;
import utilities.window.editor.*;

public class EntityButtons extends PanelButtons {


    public EntityButtons(EntityEditor entityEditor) {
        super(entityEditor);
        super.setButtonsContent (new EntityButtonsContent(this));
    }

}
