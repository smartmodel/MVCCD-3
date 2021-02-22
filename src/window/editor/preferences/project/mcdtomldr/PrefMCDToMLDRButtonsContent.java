package window.editor.preferences.project.mcdtomldr;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMCDToMLDRButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMCDToMLDRButtons PrefMCDToMLDRButtons;

    public PrefMCDToMLDRButtonsContent(PrefMCDToMLDRButtons PrefMCDToMLDRButtons) {
        super(PrefMCDToMLDRButtons);
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
