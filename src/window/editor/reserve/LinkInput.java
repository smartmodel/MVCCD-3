package window.editor.reserve;


import newEditor.PanelInput;

public class LinkInput extends PanelInput {

    public LinkInput(LinkEditor linkEditor) {
        super(linkEditor);
        super.setInputContent( new LinkInputContent(this));
    }


}
