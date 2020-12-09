package window.editor.preferences.project.mpdr.oracle;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMPDROracleButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMPDROracleButtons prefMPDROracleButtons;

    public PrefMPDROracleButtonsContent(PrefMPDROracleButtons prefMPDROracleButtons) {
        super(prefMPDROracleButtons);
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
