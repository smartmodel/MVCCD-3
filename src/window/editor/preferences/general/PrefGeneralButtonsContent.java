package window.editor.preferences.general;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefGeneralButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefGeneralButtons prefGeneralButtons;

    public PrefGeneralButtonsContent(PrefGeneralButtons prefGeneralButtons) {
        super(prefGeneralButtons);
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
