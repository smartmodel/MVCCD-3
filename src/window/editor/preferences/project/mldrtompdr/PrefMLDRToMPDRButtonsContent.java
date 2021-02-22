package window.editor.preferences.project.mldrtompdr;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMLDRToMPDRButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMLDRToMPDRButtons PrefMLDRToMPDRButtons;

    public PrefMLDRToMPDRButtonsContent(PrefMLDRToMPDRButtons PrefMLDRToMPDRButtons) {
        super(PrefMLDRToMPDRButtons);
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
