package window.editor.preferences.project.mdrformat;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.editor.PanelButtonsContentReInit;

import java.awt.event.ActionListener;

public class PrefMDRFormatButtonsContent extends PanelButtonsContentReInit implements ActionListener {

    private PrefMDRFormatButtons prefMDRFormatButtons;

    public PrefMDRFormatButtonsContent(PrefMDRFormatButtons prefMDRFormatButtons) {
        super(prefMDRFormatButtons);
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
