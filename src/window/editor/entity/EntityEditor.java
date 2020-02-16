package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDWindow;
import mcd.MCDEntities;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;

import javax.swing.tree.DefaultMutableTreeNode;

public class EntityEditor extends DialogEditor {



    public EntityEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow, node, mode);

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
