package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDWindow;
import mcd.MCDEntities;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import newEditor.DialogEditor;
import preferences.Preferences;

import javax.swing.tree.DefaultMutableTreeNode;
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
