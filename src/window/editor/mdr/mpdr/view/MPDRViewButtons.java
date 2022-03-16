package window.editor.mdr.mpdr.view;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MPDRViewButtons extends PanelButtons {


    public MPDRViewButtons(MPDRViewEditor MPDRViewEditor) {
        super(MPDRViewEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MPDRViewButtonsContent(this);
    }
}
