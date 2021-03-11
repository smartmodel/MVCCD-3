package window.editor.mdr.parameter;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDRParameterButtons extends PanelButtons {


    public MDRParameterButtons(MDRParameterEditor MDRParameterEditor) {
        super(MDRParameterEditor);
        //super.setButtonsContent (new EntityButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDRParameterButtonsContent(this);
    }
}
