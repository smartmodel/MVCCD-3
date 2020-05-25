package window.editor.entity;

import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDAttributesEditingTreat;
import utilities.window.editor.PanelNavContent;
import utilities.window.scomponents.SButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EntityNavContent extends EntityNavContentPanel implements ActionListener {



    public EntityNavContent(EntityNav entityNav) {
        super(entityNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnEntity.setEnabled(false);
        btsEnabled.remove(btnEntity);
    }

}
