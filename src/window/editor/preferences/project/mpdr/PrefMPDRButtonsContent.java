package window.editor.preferences.project.mpdr;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContentReInit;

import java.awt.event.ActionListener;

public abstract class PrefMPDRButtonsContent extends PanelButtonsContentReInit implements ActionListener {

    private PrefMPDRButtons prefMPDRButtons;

    public PrefMPDRButtonsContent(PrefMPDRButtons prefMPDRButtons) {
        super(prefMPDRButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return null;
    }

}
