package window.editor.attributes;

import window.editor.entity.EntityNavBtnContentPanel;

import java.awt.event.ActionListener;

public class AttributesNavBtnContentContentPanel extends EntityNavBtnContentPanel implements ActionListener {



    public AttributesNavBtnContentContentPanel(AttributesNavBtn attributesNav) {

        super(attributesNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnAttributes.setEnabled(false);
        btsEnabled.remove(btnAttributes);
    }

}
