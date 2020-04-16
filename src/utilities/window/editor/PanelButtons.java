package utilities.window.editor;


import utilities.window.PanelBorderLayout;

public abstract class PanelButtons extends PanelBorderLayout implements IAccessDialogEditor {

    private DialogEditor dialogEditor;
    private PanelButtonsContent buttonsContent;

    public PanelButtons(DialogEditor dialogEditor) {
        this.dialogEditor = dialogEditor;
        setButtonsContent (createButtonsContentCustom());
        super.setResizable(false);
    }

    protected abstract PanelButtonsContent createButtonsContentCustom();

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

}
