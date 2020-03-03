package window.editor.mcddatatype;

import utilities.window.editor.PanelInput;
import window.editor.entity.EntityEditor;
import window.editor.entity.EntityInputContent;

public class MCDDatatypeInput extends PanelInput  {

    public MCDDatatypeInput(MCDDatatypeEditor mcdDatatypeEditor) {
        super(mcdDatatypeEditor);
        super.setInputContent( new MCDDatatypeInputContent(this));
    }


}
