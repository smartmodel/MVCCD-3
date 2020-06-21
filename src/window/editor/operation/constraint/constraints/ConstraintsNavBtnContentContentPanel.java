package window.editor.operation.constraint.constraints;

import window.editor.entity.EntityNavBtnContentPanel;

import java.awt.event.ActionListener;

public class ConstraintsNavBtnContentContentPanel extends EntityNavBtnContentPanel implements ActionListener {


    public ConstraintsNavBtnContentContentPanel(ConstraintsNavBtn constraintsNav) {

        super(constraintsNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnConstraints.setEnabled(false);
        btsEnabled.remove(btnConstraints);
    }

}
