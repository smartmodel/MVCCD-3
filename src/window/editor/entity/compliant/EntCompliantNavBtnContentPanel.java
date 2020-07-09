package window.editor.entity.compliant;

import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDAttributesEditingTreat;
import repository.editingTreat.mcd.MCDConstraintsEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import repository.editingTreat.mcd.MCDRelEndsEditingTreat;
import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;
import utilities.window.scomponents.SButton;
import window.editor.entity.EntityNavBtnContentPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


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
