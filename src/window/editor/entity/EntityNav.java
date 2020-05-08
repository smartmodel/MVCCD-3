package window.editor.entity;


import utilities.window.editor.*;

public class EntityNav extends PanelNav {

    public EntityNav(EntityEditor entityEditor) {
        super(entityEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavContent createTabbedContentCustom() {
        return new EntityNavContent(this);
    }


}
