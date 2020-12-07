package window.editor.preferences.project.mldr;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class PrefMLDRButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefMLDRButtons prefMLDRButtons;

    public PrefMLDRButtonsContent(PrefMLDRButtons prefMLDRButtons) {
        super(prefMLDRButtons);
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
