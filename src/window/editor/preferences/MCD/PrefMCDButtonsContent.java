package window.editor.preferences.MCD;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.editor.DialogEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefMCDButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMCDButtons prefMCDButtons;

    public PrefMCDButtonsContent(PrefMCDButtons prefMCDButtons) {
        super(prefMCDButtons);
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
