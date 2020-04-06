package window.editor.entity;

import mcd.MCDEntities;
import mcd.MCDEntity;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;

import java.awt.*;

public class EntityEditor extends DialogEditor {



    public EntityEditor(Window owner,
                        MCDEntities parent,
                        MCDEntity mcdEntity,
                        String mode)  {
        super(owner, parent, mcdEntity, mode);

        super.setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        super.setInput(new EntityInput(this));
        super.setButtons (new EntityButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return "editor.entity.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.entity.update";
    }
}
