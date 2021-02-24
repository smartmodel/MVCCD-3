package utilities.window.editor;

public abstract class PanelButtonsContentReInit extends PanelButtonsContent {


    public PanelButtonsContentReInit(PanelButtons panelButtons) {
        super(panelButtons);
    }

    protected void createContent() {
        super.createContent();
        if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
            btnReInit.setVisible(true);
        }
    }


}
