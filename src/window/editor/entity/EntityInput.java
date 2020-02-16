package window.editor.entity;

import utilities.window.PanelBorderLayout;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.editor.PanelInput;

public class EntityInput extends PanelInput  {

    public EntityInput(EntityEditor entityEditor) {
        super(entityEditor);
        super.setInputContent( new EntityInputContent(this));
    }


}
