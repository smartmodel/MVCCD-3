package window.editor.entity;


import utilities.window.editor.*;

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
