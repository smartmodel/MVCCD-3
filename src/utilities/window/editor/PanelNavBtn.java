package utilities.window.editor;

import utilities.window.PanelBorderLayout;

public abstract class PanelNavBtn extends PanelBorderLayout implements IAccessDialogEditor{
    private DialogEditor dialogEditor;
    private PanelNavBtnContent content;

    public PanelNavBtn(DialogEditor dialogEditor) {
        this.dialogEditor = dialogEditor;
        setContent( createContentCustom());
        super.setResizable(false);
        content.start();
    }

    protected abstract PanelNavBtnContent createContentCustom();

    public PanelNavBtnContent getContent() {
        return content;
    }

    public void setContent(PanelNavBtnContent content) {
        this.content = content;
    }

    public DialogEditor getEditor() {
        return dialogEditor;
    }


}
