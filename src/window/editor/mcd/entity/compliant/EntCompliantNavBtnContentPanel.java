package window.editor.mcd.entity.compliant;

import window.editor.mcd.entity.EntityNavBtnContentPanel;

import java.awt.event.ActionListener;


public class EntCompliantNavBtnContentPanel extends EntityNavBtnContentPanel implements ActionListener {

    public EntCompliantNavBtnContentPanel(EntCompliantNavBtn entCompliantNavBtn) {

        super(entCompliantNavBtn);

    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnEntCompliant.setEnabled(false);
        btsEnabled.remove(btnEntCompliant);
    }
}
