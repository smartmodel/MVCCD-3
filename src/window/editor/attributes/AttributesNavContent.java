package window.editor.attributes;

import mcd.MCDContAttributes;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import utilities.window.editor.PanelNavContent;
import utilities.window.scomponents.SButton;
import window.editor.entity.EntityNavContentPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AttributesNavContent extends EntityNavContentPanel implements ActionListener {



    public AttributesNavContent(AttributesNav attributesNav) {

        super(attributesNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnAttributes.setEnabled(false);
        btsEnabled.remove(btnAttributes);
    }

}
