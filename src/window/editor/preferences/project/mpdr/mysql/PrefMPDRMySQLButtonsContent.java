package window.editor.preferences.project.mpdr.mysql;

import window.editor.preferences.project.mpdr.PrefMPDRButtonsContent;

import java.awt.event.ActionListener;

public class PrefMPDRMySQLButtonsContent extends PrefMPDRButtonsContent implements ActionListener {

    private PrefMPDRMySQLButtons prefMPDRMySQLButtons;

    public PrefMPDRMySQLButtonsContent(PrefMPDRMySQLButtons prefMPDRMySQLButtons) {
        super(prefMPDRMySQLButtons);
    }


    @Override
    protected String getHelpFileName() {
        return null;
    }

}
