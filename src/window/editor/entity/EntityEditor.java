package window.editor.entity;

import mcd.MCDContEntities;
import mcd.MCDEntity;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class EntityEditor extends DialogEditor {



    public EntityEditor(Window owner,
                        MCDContEntities parent,
                        MCDEntity mcdEntity,
                        String mode)  {
        super(owner, parent, mcdEntity, mode);
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
