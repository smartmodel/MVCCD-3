package window.editor.entity;

import mcd.MCDContEntities;
import mcd.MCDEntity;
import utilities.window.editor.*;
import preferences.Preferences;

import java.awt.*;

public class EntityEditor extends DialogEditorNav {



    public EntityEditor(Window owner,
                        MCDContEntities parent,
                        MCDEntity mcdEntity,
                        String mode)  {
        super(owner, parent, mcdEntity, mode, DialogEditor.NOTHING);
/*
        super.setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        super.setInput(new EntityInput(this));
        super.setButtons (new EntityButtons(this));

        super.start();

 */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new EntityButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new EntityInput(this);
    }

    @Override
    protected PanelNav getNavCustom() {
        return new EntityNav(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
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
