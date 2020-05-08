package window.editor.attributes;


import utilities.window.editor.PanelNav;
import utilities.window.editor.PanelNavContent;

public class AttributesNav extends PanelNav {

    public AttributesNav(AttributesEditor attributesEditor) {
        super(attributesEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavContent createTabbedContentCustom() {

        return new AttributesNavContent(this);
    }


}
