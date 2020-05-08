package utilities.window.editor;

import utilities.window.PanelBorderLayout;

public abstract class PanelInput extends PanelBorderLayout implements IAccessDialogEditor {

    private DialogEditor dialogEditor;
    private PanelInputContent inputContent;


    public PanelInput(DialogEditor dialogEditor) {
        this.dialogEditor = dialogEditor;
        PanelInputContent panelInputContent = createInputContentCustom();
        setInputContent( panelInputContent);
        panelInputContent.start();
    }
/*
    public PanelInput(DialogEditor dialogEditor, PanelInputContent panelInputContent) {

        this.dialogEditor = dialogEditor;
        //PanelInputContent panelInputContent = createInputContentCustom();
        setInputContent( panelInputContent);
        panelInputContent.start();

    }

 */

    protected abstract PanelInputContent createInputContentCustom();

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

}
