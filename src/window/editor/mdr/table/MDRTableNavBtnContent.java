package window.editor.mdr.table;

import java.awt.event.ActionListener;

public class MDRTableNavBtnContent extends MDRTableNavBtnContentPanel implements ActionListener {



    public MDRTableNavBtnContent(MDRTableNavBtn entityNav) {
        super(entityNav);
    }

    @Override
    protected void createContentCustom() {
        super.createContentCustom();
        btnTable.setEnabled(false);
        btsEnabled.remove(btnTable);
    }

}
