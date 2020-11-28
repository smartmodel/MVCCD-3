package window.editor.preferences.project.mdr;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMDRButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMDRButtons prefMDRButtons;

    public PrefMDRButtonsContent(PrefMDRButtons prefMDRButtons) {
        super(prefMDRButtons);
    }


    @Override
    protected String getHelpFileName() {
        return null;
    }

    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return null;
    }

}
