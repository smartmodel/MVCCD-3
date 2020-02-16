package window.editor.preferences.general;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;
import window.editor.preferences.MCD.PrefMCDButtons;

import java.awt.event.ActionListener;

public class PrefGeneralButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefGeneralButtons prefGeneralButtons;

    public PrefGeneralButtonsContent(PrefGeneralButtons prefGeneralButtons) {
        super(prefGeneralButtons);
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
