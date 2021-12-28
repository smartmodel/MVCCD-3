package window.editor.preferences.project.mpdr.oracle;

import window.editor.preferences.project.mpdr.PrefMPDRButtonsContent;

import java.awt.event.ActionListener;

public class PrefMPDROracleButtonsContent extends PrefMPDRButtonsContent implements ActionListener {

    private PrefMPDROracleButtons prefMPDROracleButtons;

    public PrefMPDROracleButtonsContent(PrefMPDROracleButtons prefMPDROracleButtons) {
        super(prefMPDROracleButtons);
    }


    @Override
    protected String getHelpFileName() {
        return null;
    }

}
