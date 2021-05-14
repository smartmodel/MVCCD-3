package window.editor.mcd.entity;

import console.ViewLogsManager;
import mcd.MCDEntity;
import messages.MessagesBuilder;
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

        String tabProperty = "";

        try {
            Object source = e.getSource();
            MCDEntity mcdEntity = getMCDEntity();

            getEditor().myDispose();

            if (source == btnEntity) {
                tabProperty = "editor.entity.tab.exception.properties";
                new MCDEntityEditingTreat().treatUpdate(getEditor().getOwner(),
                        getMCDEntity());
            }

            if (source == btnAttributes) {
                tabProperty = "editor.entity.tab.exception.attributes";
                new MCDAttributesEditingTreat().treatRead(getEditor().getOwner(),
                        getMCDEntity().getMCDContAttributes());
            }

            if (source == btnConstraints) {
                tabProperty = "editor.entity.tab.exception.constraints";
                new MCDConstraintsEditingTreat().treatUpdate(getEditor().getOwner(),
                        getMCDEntity().getMCDContConstraints());
            }

            if (source == btnRelations) {
                tabProperty = "editor.entity.tab.exception.relations";
                new MCDRelEndsEditingTreat().treatUpdate(getEditor().getOwner(),
                        getMCDEntity().getMCDContRelEnds());
            }

            if (source == btnEntCompliant) {
                // Le contrôle de conformité se fait au sein du formulaire
                // EntCompliantInputContent
                tabProperty = "editor.entity.tab.exception.compliant";
                new MCDEntCompliantEditingTreat().treatRead(getEditor().getOwner(),
                        getMCDEntity());
            }

            if (source == btnMLDR) {
                tabProperty = "editor.entity.tab.exception.mldr";
                new MCDEntMLDREditingTreat().treatUpdate(getEditor().getOwner(),
                        getMCDEntity());
            }
        } catch (Exception exception ){
            String tabMessage = MessagesBuilder.getMessagesProperty(tabProperty) ;
            String message = MessagesBuilder.getMessagesProperty("editor.entity.tab.exception",
                    new String[] {tabMessage, getMCDEntity().getName()} );
            ViewLogsManager.catchException(exception, getEditor(), message);
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
