package window.editor.naming;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class NamingButtons extends PanelButtons {


    public NamingButtons(NamingEditor NamingEditor) {
        super(NamingEditor);
        //super.setButtonsContent (new MCDDatatypeButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new NamingButtonsContent(this);
    }
}
