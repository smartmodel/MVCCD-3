package window.editor.preferences.application;

import main.MVCCDElement;
import newEditor.PanelButtonsContent;
import preferences.Preferences;

import java.awt.event.ActionListener;

public class PrefApplicationButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefApplicationButtons prefApplicationButtons;

    public PrefApplicationButtonsContent(PrefApplicationButtons prefApplicationButtons) {
        super(prefApplicationButtons);
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
