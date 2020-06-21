package window.editor.attributes;


import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;

public class AttributesNavBtn extends PanelNavBtn {

    public AttributesNavBtn(AttributesEditorBtn attributesEditor) {
        super(attributesEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavBtnContent createContentCustom() {

        return new AttributesNavBtnContentContentPanel(this);
    }


}
