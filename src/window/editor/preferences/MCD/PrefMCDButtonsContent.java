package window.editor.preferences.MCD;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMCDButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMCDButtons prefMCDButtons;

    public PrefMCDButtonsContent(PrefMCDButtons prefMCDButtons) {
        super(prefMCDButtons);
    }


    @Override
    protected String getHelpFileName() {
        return null;
    }


    @Override
    protected MVCCDElement createNewMVCCDElement() {
        return null;
    }
}
