package window.editor.mcd.entity;


import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;

public class EntityNavBtn extends PanelNavBtn {

    public EntityNavBtn(EntityEditor entityEditor) {
        super(entityEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavBtnContent createContentCustom() {
        return new EntityNavBtnContent(this);
    }


}
