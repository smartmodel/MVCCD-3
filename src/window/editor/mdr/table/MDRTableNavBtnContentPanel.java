package window.editor.mdr.table;

import console.ViewLogsManager;
import exceptions.service.ExceptionService;
import mdr.MDRTable;
import messages.MessagesBuilder;
import repository.editingTreat.mdr.MDRTableEditingTreat;
import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;
import utilities.window.scomponents.SButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MDRTableNavBtnContentPanel extends PanelNavBtnContent implements ActionListener {

    protected SButton btnTable;
    protected SButton btnAttributes ;
    protected SButton btnConstraints ;
    protected SButton btnRelations ;
    protected SButton btnEntCompliant ;
    protected SButton btnMLDR ;

    protected ArrayList<SButton> btsEnabled = new ArrayList<SButton>();

    public MDRTableNavBtnContentPanel(PanelNavBtn panelNavBtn) {
        super(panelNavBtn);
    }

    @Override
    protected void createContentCustom() {

        btnTable = new SButton("Généralités");
        btnTable.addActionListener(this);

        /*
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

         */

        panelNavContentCustom.add(btnTable);
        /*
        panelNavContentCustom.add(btnAttributes);
        panelNavContentCustom.add(btnConstraints);
        panelNavContentCustom.add(btnRelations);
        panelNavContentCustom.add(btnEntCompliant);
        panelNavContentCustom.add(btnMLDR);

         */


        btsEnabled.add(btnTable);
        /*
        btsEnabled.add(btnAttributes);
        btsEnabled.add(btnConstraints);
        btsEnabled.add(btnRelations);
        btsEnabled.add(btnEntCompliant);
        btsEnabled.add(btnMLDR);

         */


        this.add(panelNavContentCustom);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String tabProperty = "";

        try {
        Object source = e.getSource();

        MDRTable mdrTable = getMDRTable();

        if (source == btnTable) {
            tabProperty = "editor.table.tab.exception.properties";
            getEditor().myDispose();
            new MDRTableEditingTreat().treatUpdate(getEditor().getOwner(),
                    getMDRTable());
        }
/*
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

        }


 */
        } catch (Exception exception){
            ExceptionService.exceptionUnhandled(exception, getEditor(), getMDRTable(),
                    "editor.table.tab.exception", tabProperty);
        }
    }

    private MDRTable getMDRTable(){
        MDRTable mdrTable = null;
        if ( getEditor().getMvccdElementCrt() instanceof MDRTable ){
            mdrTable = (MDRTable) getEditor().getMvccdElementCrt();
        } else {
            mdrTable = (MDRTable) getEditor().getMvccdElementCrt().getParent();
        }
        return mdrTable;
    }

    @Override
    protected ArrayList<SButton> getBtsEnabled() {
        return btsEnabled;
    }

}
