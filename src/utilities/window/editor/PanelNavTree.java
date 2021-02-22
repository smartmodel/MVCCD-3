package utilities.window.editor;

import utilities.window.PanelBorderLayout;

import javax.swing.tree.TreePath;

public abstract class PanelNavTree extends PanelBorderLayout implements IAccessDialogEditor{
    private DialogEditorNavTree dialogEditorNavTree;
    private PanelNavTreeContent content;

    public PanelNavTree(DialogEditorNavTree dialogEditorNavTree) {
        this.dialogEditorNavTree = dialogEditorNavTree;
        setContent( createContentCustom());
        super.setResizable(false);
        content.start();
    }

    protected abstract PanelNavTreeContent createContentCustom();

    public PanelNavTreeContent getContent() {
        return content;
    }

    public void setContent(PanelNavTreeContent content) {
        this.content = content;
    }

    public DialogEditor getEditor() {
        return dialogEditorNavTree;
    }


}
