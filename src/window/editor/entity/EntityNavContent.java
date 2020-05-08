package window.editor.entity;

import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDAttributesEditingTreat;
import utilities.window.editor.PanelNavContent;
import utilities.window.scomponents.SButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EntityNavContent extends PanelNavContent implements ActionListener {

    private SButton btnEntity ;
    private SButton btnAttributes ;

    private ArrayList<SButton> btsEnabled = new ArrayList<SButton>();

    public EntityNavContent(EntityNav entityNav) {
        super(entityNav);
    }

    @Override
    protected void createContentCustom() {

        btnEntity = new SButton("Généralités");
        btnEntity.addActionListener(this);
        btnAttributes = new SButton("Attributes");
        btnAttributes.addActionListener(this);

        panelNavContentCustom.add(btnEntity);
        panelNavContentCustom.add(btnAttributes);

        btnEntity.setEnabled(false);
        btsEnabled.add(btnAttributes);

        this.add(panelNavContentCustom);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        MCDEntity mcdentity = (MCDEntity) getEditor().getMvccdElementCrt();

        Object source = e.getSource();

        if (source == btnEntity) {
            //Editeur sélectionné
        }
        if (source == btnAttributes) {
            getEditor().dispose();
            new MCDAttributesEditingTreat().treatRead(getEditor().getOwner(),
                    mcdentity.getMCDContAttributes());
        }
    }

    @Override
    protected ArrayList<SButton> getBtsEnabled() {
        return btsEnabled;
    }
}
