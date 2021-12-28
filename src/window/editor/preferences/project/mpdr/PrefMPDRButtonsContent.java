package window.editor.preferences.project.mpdr;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public abstract class PrefMPDRButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMPDRButtons prefMPDRButtons;

    public PrefMPDRButtonsContent(PrefMPDRButtons prefMPDRButtons) {
        super(prefMPDRButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return null;
    }

}
