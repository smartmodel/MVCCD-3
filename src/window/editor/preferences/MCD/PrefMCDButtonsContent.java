package window.editor.preferences.MCD;

import main.MVCCDElement;
import newEditor.PanelButtonsContent;
import preferences.Preferences;

import java.awt.event.ActionEvent;
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
