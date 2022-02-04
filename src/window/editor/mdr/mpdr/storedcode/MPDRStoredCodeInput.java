package window.editor.mdr.mpdr.storedcode;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MPDRStoredCodeInput extends PanelInput {

    public MPDRStoredCodeInput(MPDRStoredCodeEditor MPDRStoredCodeEditor) {
        super(MPDRStoredCodeEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MPDRStoredCodeInputContent(this);
    }

}
