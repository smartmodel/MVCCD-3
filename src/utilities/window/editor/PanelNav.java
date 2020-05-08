package utilities.window.editor;

import utilities.window.PanelBorderLayout;

public abstract class PanelNav extends PanelBorderLayout implements IAccessDialogEditor{
    private DialogEditor dialogEditor;
    private PanelNavContent tabbedContent;

    public PanelNav(DialogEditor dialogEditor) {
        this.dialogEditor = dialogEditor;
        setTabbedContent( createTabbedContentCustom());
        super.setResizable(false);
        tabbedContent.start();
    }

    protected abstract PanelNavContent createTabbedContentCustom();

    public PanelNavContent getTabbedContent() {
        return tabbedContent;
    }

    public void setTabbedContent(PanelNavContent tabbedContent) {
        this.tabbedContent = tabbedContent;
    }

    public DialogEditor getEditor() {
        return dialogEditor;
    }


}
