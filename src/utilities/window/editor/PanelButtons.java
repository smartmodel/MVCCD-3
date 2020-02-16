package utilities.window.editor;

import utilities.window.PanelBorderLayout;

public abstract class PanelButtons extends PanelBorderLayout implements IAccessDialogEditor{

    private DialogEditor dialogEditor;
    private PanelButtonsContent buttonsContent;

    private boolean readOnly = false;

    public PanelButtons(DialogEditor dialogEditor) {
        this.dialogEditor = dialogEditor;
        super.setResizable(false);
    }

    public PanelButtonsContent getButtonsContent() {
        return buttonsContent;
    }

    public void setButtonsContent(PanelButtonsContent buttonsContent) {
        this.buttonsContent = buttonsContent;
        super.setPanelContent(buttonsContent);
    }

    public DialogEditor getEditor(){
        return dialogEditor;
    }

    public PanelInput getInput(){
        return dialogEditor.getInput();
    }

    public PanelInputContent getInputContent(){
        return getInput().getInputContent();
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        if (readOnly){
            buttonsContent.setReadOnly(readOnly);
        }
    }
}
