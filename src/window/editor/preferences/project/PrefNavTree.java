package window.editor.preferences.project;


import utilities.window.editor.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class PrefNavTree extends PanelNavTree {


    public PrefNavTree(DialogEditorNavTree dialogEditorNavTree) {
        super(dialogEditorNavTree);
    }


    @Override
    protected PanelNavTreeContent createContentCustom() {
        return new PrefNavTreeContent(this);
    }


}
