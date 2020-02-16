package utilities.window.editor;

import utilities.window.PanelBorderLayout;

public abstract class PanelInput extends PanelBorderLayout implements IAccessDialogEditor{

    private DialogEditor dialogEditor;
    private PanelInputContent inputContent;

    private boolean readOnly = false;

    public PanelInput(DialogEditor dialogEditor) {
        this.dialogEditor = dialogEditor;
    }

    public PanelInputContent getInputContent() {
        return inputContent;
    }

    public void setInputContent(PanelInputContent inputContent) {
        this.inputContent = inputContent;
    }

    public DialogEditor getEditor(){
        return dialogEditor;
    }

    public PanelButtons getButtons(){
        return dialogEditor.getButtons();
    }

    public PanelButtonsContent getButtonsContent(){
        return getButtons().getButtonsContent();
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        if (readOnly){
            inputContent.setReadOnly(readOnly);
        }
    }
}
