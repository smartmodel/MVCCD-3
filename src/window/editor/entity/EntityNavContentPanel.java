package window.editor.entity;

import mcd.MCDContAttributes;
import mcd.MCDContConstraints;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDAttributesEditingTreat;
import repository.editingTreat.mcd.MCDConstraintsEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import utilities.window.editor.PanelNav;
import utilities.window.editor.PanelNavContent;
import utilities.window.scomponents.SButton;
import window.editor.operation.constraint.constraints.ConstraintsNav;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class EntityNavContentPanel extends PanelNavContent implements ActionListener {

    protected SButton btnEntity ;
    protected SButton btnAttributes ;
    protected SButton btnConstraints ;

    protected ArrayList<SButton> btsEnabled = new ArrayList<SButton>();

    public EntityNavContentPanel(PanelNav panelNav) {
        super(panelNav);
    }

    @Override
    protected void createContentCustom() {

        btnEntity = new SButton("Généralités");
        btnEntity.addActionListener(this);
        //btnEntity.setEnabled(false);
        btnAttributes = new SButton("Attributes");
        btnAttributes.addActionListener(this);
        //btnAttributes.setEnabled(false);
        btnConstraints = new SButton("Contraintes");
        btnConstraints.addActionListener(this);
        //btnConstraints.setEnabled(false);

        panelNavContentCustom.add(btnEntity);
        panelNavContentCustom.add(btnAttributes);
        panelNavContentCustom.add(btnConstraints);


        btsEnabled.add(btnEntity);
        btsEnabled.add(btnAttributes);
        btsEnabled.add(btnConstraints);

        this.add(panelNavContentCustom);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        Object source = e.getSource();

        MCDEntity mcdEntity = getMCDEntity();

        if (source == btnEntity) {
            //MCDContAttributes mcdContAttributes = (MCDContAttributes) getEditor().getMvccdElementCrt();
            getEditor().myDispose();
            new MCDEntityEditingTreat().treatUpdate(getEditor().getOwner(),
                    getMCDEntity());
        }

        if (source == btnAttributes) {
            getEditor().myDispose();
            new MCDAttributesEditingTreat().treatRead(getEditor().getOwner(),
                    getMCDEntity().getMCDContAttributes());
        }

        if (source == btnConstraints) {
            //MCDContConstraints mcdContConstraints = (MCDContConstraints) getEditor().getMvccdElementCrt();
            getEditor().myDispose();
            new MCDConstraintsEditingTreat().treatUpdate(getEditor().getOwner(),
                    getMCDEntity().getMCDContConstraints());
        }
    }

    private MCDEntity getMCDEntity(){
        MCDEntity mcdEntity = null;
        if ( getEditor().getMvccdElementCrt() instanceof MCDEntity ){
            mcdEntity = (MCDEntity) getEditor().getMvccdElementCrt();
        } else {
            mcdEntity = (MCDEntity) getEditor().getMvccdElementCrt().getParent();
        }
        return mcdEntity;
    }

    @Override
    protected ArrayList<SButton> getBtsEnabled() {
        return btsEnabled;
    }

}
