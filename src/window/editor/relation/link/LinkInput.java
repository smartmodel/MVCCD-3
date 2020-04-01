package window.editor.relation.link;


import newEditor.PanelInput;

public class LinkInput extends PanelInput {

    public LinkInput(LinkEditor linkEditor) {
        super(linkEditor);
        super.setInputContent( new LinkInputContent(this));
    }


}
