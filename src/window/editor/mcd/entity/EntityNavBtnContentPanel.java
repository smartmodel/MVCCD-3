package window.editor.mcd.entity;

import mcd.MCDEntity;
import repository.editingTreat.mcd.*;
import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;
import utilities.window.scomponents.SButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class EntityNavBtnContentPanel extends PanelNavBtnContent implements ActionListener {

    protected SButton btnEntity ;
    protected SButton btnAttributes ;
    protected SButton btnConstraints ;
    protected SButton btnRelations ;
    protected SButton btnEntCompliant ;
    protected SButton btnMLDR ;

    protected ArrayList<SButton> btsEnabled = new ArrayList<SButton>();

    public EntityNavBtnContentPanel(PanelNavBtn panelNavBtn) {
        super(panelNavBtn);
    }

    @Override
    protected void createContentCustom() {

        btnEntity = new SButton("Généralités");
        btnEntity.addActionListener(this);

        btnAttributes = new SButton("Attributes");
        btnAttributes.addActionListener(this);

        btnConstraints = new SButton("Contraintes");
        btnConstraints.addActionListener(this);

        btnRelations = new SButton("Relations");
        btnRelations.addActionListener(this);

        btnEntCompliant = new SButton("Conformité");
        btnEntCompliant.addActionListener(this);

        btnMLDR = new SButton("MLD-R");
        btnMLDR.addActionListener(this);

        panelNavContentCustom.add(btnEntity);
        panelNavContentCustom.add(btnAttributes);
        panelNavContentCustom.add(btnConstraints);
        panelNavContentCustom.add(btnRelations);
        panelNavContentCustom.add(btnEntCompliant);
        panelNavContentCustom.add(btnMLDR);


        btsEnabled.add(btnEntity);
        btsEnabled.add(btnAttributes);
        btsEnabled.add(btnConstraints);
        btsEnabled.add(btnRelations);
        btsEnabled.add(btnEntCompliant);
        btsEnabled.add(btnMLDR);

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
            getEditor().myDispose();
            new MCDConstraintsEditingTreat().treatUpdate(getEditor().getOwner(),
                    getMCDEntity().getMCDContConstraints());
        }

        if (source == btnRelations) {
            getEditor().myDispose();
            new MCDRelEndsEditingTreat().treatUpdate(getEditor().getOwner(),
                    getMCDEntity().getMCDContRelEnds());
        }

        if (source == btnEntCompliant) {
            getEditor().myDispose();
            // Le contrôle de conformité se fait au sein du formulaire
            // EntCompliantInputContent
            new MCDEntCompliantEditingTreat().treatRead(getEditor().getOwner(),
                    getMCDEntity());
        }

        if (source == btnMLDR) {
            getEditor().myDispose();
            new MCDEntMLDREditingTreat().treatUpdate(getEditor().getOwner(),
                    getMCDEntity());
            /*
            new MCDEntCompliantEditingTreat().treatCompliant(getEditor().getOwner(),
                    getMCDEntity());

             */
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
