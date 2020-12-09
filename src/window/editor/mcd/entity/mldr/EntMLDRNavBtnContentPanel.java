package window.editor.mcd.entity.mldr;

import window.editor.mcd.entity.EntityNavBtnContentPanel;

import java.awt.event.ActionListener;


public class EntMLDRNavBtnContentPanel extends EntityNavBtnContentPanel implements ActionListener {

    public EntMLDRNavBtnContentPanel(EntMLDRNavBtn entMldrNavBtn) {

        super(entMldrNavBtn);

    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnMLDR.setEnabled(false);
        btsEnabled.remove(btnMLDR);
    }
}
