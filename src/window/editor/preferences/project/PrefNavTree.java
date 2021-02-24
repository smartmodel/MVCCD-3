package window.editor.preferences.project;


import utilities.window.editor.DialogEditorNavTree;
import utilities.window.editor.PanelNavTree;
import utilities.window.editor.PanelNavTreeContent;

public class PrefNavTree extends PanelNavTree {


    public PrefNavTree(DialogEditorNavTree dialogEditorNavTree) {
        super(dialogEditorNavTree);
    }


    @Override
    protected PanelNavTreeContent createContentCustom() {
        return new PrefNavTreeContent(this);
    }


}
