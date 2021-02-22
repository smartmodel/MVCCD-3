package window.editor.mcd.relation.link;

import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class LinkInput extends PanelInput {

    public LinkInput(LinkEditor linkEditor) {
        super(linkEditor);
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new LinkInputContent(this);
    }
}
