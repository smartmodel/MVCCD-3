package window.editor.mcd.relation.link;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class LinkButtons extends PanelButtons {


    public LinkButtons(LinkEditor linkEditor) {
        super(linkEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new LinkButtonsContent(this);
    }
}
