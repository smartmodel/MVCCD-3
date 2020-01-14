package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDWindow;
import preferences.Preferences;
import utilities.window.DialogEditor;

import javax.swing.tree.DefaultMutableTreeNode;

public class EntityWindow extends DialogEditor {

    private EntityEditor editor;
    private EntityButtons buttons;
    private DefaultMutableTreeNode node = null;   // Parent pour la création et lui-même pour la modification
    private MVCCDElement mvccdElement = null;     // Parent pour la création et lui-même pour la modification
    private String mode;  // Création ou modification



    public EntityWindow(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow);
        this.node = node;
        this.mode = mode;
        mvccdElement = (MVCCDElement) node.getUserObject();
        setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);

        editor = new EntityEditor(this);
        super.setEditor(editor);
        buttons = new EntityButtons(this);
        super.setButtons (buttons);

        super.start();
    }

    public EntityEditor getEditor() {
        return editor;
    }

    public EntityButtons getButtons() {
        return buttons;
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

    public MVCCDElement getMvccdElement() {
        return mvccdElement;
    }

    public String getMode() {
        return mode;
    }
}
