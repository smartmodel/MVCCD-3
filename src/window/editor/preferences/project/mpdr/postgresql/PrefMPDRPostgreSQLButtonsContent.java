package window.editor.preferences.project.mpdr.postgresql;

import window.editor.preferences.project.mpdr.PrefMPDRButtonsContent;

import java.awt.event.ActionListener;

public class PrefMPDRPostgreSQLButtonsContent extends PrefMPDRButtonsContent implements ActionListener {

    private PrefMPDRPostgreSQLButtons prefMPDRPostgreSQLButtons;

    public PrefMPDRPostgreSQLButtonsContent(PrefMPDRPostgreSQLButtons prefMPDRPostgreSQLButtons) {
        super(prefMPDRPostgreSQLButtons);
    }


    @Override
    protected String getHelpFileName() {
        return null;
    }

}
