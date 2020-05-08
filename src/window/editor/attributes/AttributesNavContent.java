package window.editor.attributes;

import mcd.MCDContAttributes;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import utilities.window.editor.PanelNavContent;
import utilities.window.scomponents.SButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AttributesNavContent extends PanelNavContent implements ActionListener {

    private SButton btnEntity ;
    private SButton btnAttributes ;

    private ArrayList<SButton> btsEnabled = new ArrayList<SButton>();

    public AttributesNavContent(AttributesNav attributesNav) {

        super(attributesNav);
    }

    @Override
    protected void createContentCustom() {

        btnEntity = new SButton("Généralités");
        btnEntity.addActionListener(this);
        btnAttributes = new SButton("Attributes");
        btnAttributes.addActionListener(this);

        panelNavContentCustom.add(btnEntity);
        panelNavContentCustom.add(btnAttributes);

        btnAttributes.setEnabled(false);
        btsEnabled.add(btnEntity);

        this.add(panelNavContentCustom);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        MCDContAttributes mcdContAttributes = (MCDContAttributes) getEditor().getMvccdElementCrt();

        Object source = e.getSource();

        if (source == btnEntity) {
            getEditor().dispose();
            new MCDEntityEditingTreat().treatUpdate(getEditor().getOwner(),
                    (MCDEntity) mcdContAttributes.getParent());
        }
        if (source == btnAttributes) {
            //Editeur sélectionné
        }
    }

    @Override
    protected ArrayList<SButton> getBtsEnabled() {
        return btsEnabled;
    }
}
