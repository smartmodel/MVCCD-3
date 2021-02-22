package window.editor.preferences.project.mldr;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMLDRButtons extends PanelButtons {

    public PrefMLDRButtons(PrefMLDREditor prefMLDREditor) {
        super(prefMLDREditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMLDRButtonsContent(this);
    }
}
