package window.editor.preferences.project.mpdr.mysql;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMPDRMySQLButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMPDRMySQLButtons prefMPDRMySQLButtons;

    public PrefMPDRMySQLButtonsContent(PrefMPDRMySQLButtons prefMPDRMySQLButtons) {
        super(prefMPDRMySQLButtons);
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
