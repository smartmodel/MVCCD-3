package window.editor.preferences.project.mcd;

import main.MVCCDElement;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMCDButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMCDButtons prefMCDButtons;

    public PrefMCDButtonsContent(PanelButtons prefMCDButtons) {
        super(prefMCDButtons);
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
