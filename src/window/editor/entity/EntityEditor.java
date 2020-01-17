package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDWindow;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;

import javax.swing.tree.DefaultMutableTreeNode;

public class EntityEditor extends DialogEditor {

    private EntityInput editor;
    private EntityButtons buttons;



    public EntityEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow);
        super.setMode(mode);
        super.setNode(node);
        super.setMvccdElement ((MVCCDElement) node.getUserObject());
        setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        editor = new EntityInput(this);
        super.setInput(editor);
        buttons = new EntityButtons(this);
        super.setButtons (buttons);

        super.start();
    }

    public EntityInput getInput() {
        return editor;
    }

    public EntityButtons getButtons() {
        return buttons;
    }
}
