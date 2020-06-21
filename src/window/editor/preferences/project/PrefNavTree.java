package window.editor.preferences.project;


import utilities.window.editor.*;

public class PrefNavTree extends PanelNavTree {

    public PrefNavTree(DialogEditorNavTree dialogEditorNavTree) {
        super(dialogEditorNavTree);
    }


    @Override
    protected PanelNavTreeContent createContentCustom() {
        return new PrefNavTreeContent(this);
    }


}
