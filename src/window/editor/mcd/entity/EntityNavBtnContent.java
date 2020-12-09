package window.editor.mcd.entity;

import java.awt.event.ActionListener;

public class EntityNavBtnContent extends EntityNavBtnContentPanel implements ActionListener {



    public EntityNavBtnContent(EntityNavBtn entityNav) {
        super(entityNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnEntity.setEnabled(false);
        btsEnabled.remove(btnEntity);
    }

}
