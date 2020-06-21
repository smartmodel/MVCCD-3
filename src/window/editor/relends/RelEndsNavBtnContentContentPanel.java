package window.editor.relends;

import window.editor.entity.EntityNavBtnContentPanel;

import java.awt.event.ActionListener;

public class RelEndsNavBtnContentContentPanel extends EntityNavBtnContentPanel implements ActionListener {


    public RelEndsNavBtnContentContentPanel(RelEndsNavBtn relEndsNav) {

        super(relEndsNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnRelations.setEnabled(false);
        btsEnabled.remove(btnRelations);
    }

}
