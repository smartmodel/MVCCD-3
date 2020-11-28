package utilities.window.editor;

import main.MVCCDElement;
import window.editor.preferences.project.mdrformat.PrefMDRFormatButtons;

import java.awt.event.ActionListener;

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
