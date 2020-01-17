package window.preferences;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.IAccessDialogEditor;
import utilities.window.editor.PanelButtons;

public class PrefButtons extends PanelButtons {

    public PrefButtons(PrefEditor prefEditor) {
        super(prefEditor);
        PrefButtonsContent buttonsContent = new PrefButtonsContent(this);
        super.setButtonsContent(buttonsContent);

    }


}
