package window.editor.preferences.application;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefApplicationButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefApplicationButtons prefApplicationButtons;

    public PrefApplicationButtonsContent(PrefApplicationButtons prefApplicationButtons) {
        super(prefApplicationButtons);
    }

    @Override
    public Integer getWidthWindow() {
        return Preferences.PREFERENCES_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return null;
    }


    @Override
    protected MVCCDElement createNewMVCCDElement() {
        return null;
    }

    @Override
    protected void completeNewMVCCDElement(MVCCDElement mvccdElement) {

    }
}
