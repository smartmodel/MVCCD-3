package window.editor.attributes;

import window.editor.entity.EntityNavBtnContentPanel;

import java.awt.event.ActionListener;

public class AttributesNavBtnContentPanel extends EntityNavBtnContentPanel implements ActionListener {



    public AttributesNavBtnContentPanel(AttributesNavBtn attributesNav) {

        super(attributesNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnAttributes.setEnabled(false);
        btsEnabled.remove(btnAttributes);
    }

}
