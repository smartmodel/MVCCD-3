package window.editor.mdr.mpdr.view;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MPDRViewInput extends PanelInput {

    public MPDRViewInput(MPDRViewEditor MPDRViewEditor) {
        super(MPDRViewEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MPDRViewInputContent(this);
    }

}
