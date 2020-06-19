package window.editor.relends;

import window.editor.entity.EntityNavContentPanel;

import java.awt.event.ActionListener;

public class RelEndsNavContent extends EntityNavContentPanel implements ActionListener {


    public RelEndsNavContent(RelEndsNav relEndsNav) {

        super(relEndsNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnRelations.setEnabled(false);
        btsEnabled.remove(btnRelations);
    }

}
