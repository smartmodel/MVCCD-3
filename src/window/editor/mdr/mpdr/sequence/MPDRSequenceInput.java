package window.editor.mdr.mpdr.sequence;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MPDRSequenceInput extends PanelInput {

    public MPDRSequenceInput(MPDRSequenceEditor MPDRSequenceEditor) {
        super(MPDRSequenceEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        return new MPDRSequenceInputContent(this);
    }

}
