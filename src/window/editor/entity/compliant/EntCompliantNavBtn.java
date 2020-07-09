package window.editor.entity.compliant;


import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;

public class EntCompliantNavBtn extends PanelNavBtn {

    public EntCompliantNavBtn(EntCompliantEditorBtn entCompliantEditorBtn) {
        super(entCompliantEditorBtn);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavBtnContent createContentCustom() {
        return new EntCompliantNavBtnContentPanel(this);
    }


}
