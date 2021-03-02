package window.editor.preferences.project.mpdr.postgresql;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMPDRPostgreSQLButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMPDRPostgreSQLButtons prefMPDRPostgreSQLButtons;

    public PrefMPDRPostgreSQLButtonsContent(PrefMPDRPostgreSQLButtons prefMPDRPostgreSQLButtons) {
        super(prefMPDRPostgreSQLButtons);
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
