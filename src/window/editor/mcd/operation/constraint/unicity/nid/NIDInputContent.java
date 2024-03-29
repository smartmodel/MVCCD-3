package window.editor.mcd.operation.constraint.unicity.nid;

import m.MElement;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDAssEnd;
import mcd.MCDContConstraints;
import mcd.MCDNID;
import mcd.MCDOperation;
import mcd.services.MCDNIDService;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mcd.MCDNIDParameterEditingTreat;
import repository.editingTreat.mcd.MCDNIDParameterTransientEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.services.STableService;
import utilities.window.services.PanelService;
import window.editor.mcd.operation.OperationParamTableColumn;
import window.editor.mcd.operation.constraint.unicity.UnicityInput;
import window.editor.mcd.operation.constraint.unicity.UnicityInputContent;
import window.editor.mcd.operation.parameter.ParameterEditor;
import window.editor.mcd.operation.parameter.ParameterTransientEditor;

import javax.swing.*;
import java.awt.*;

public class NIDInputContent extends UnicityInputContent {

    private JLabel labelLienProg ;
    private SCheckBox fieldLienProg ;



    //TODO-1 Mettre une constante globale int = -1
    private int scopeForCheckInput = -1;



    public NIDInputContent(UnicityInput unicityInput)     {

        super(unicityInput);
    }

    public NIDInputContent(MVCCDElement element)     {

        super(element);
    }

    @Override
    public void createContentCustom() {

        super.createContentCustom();

        labelLienProg = new JLabel("Lien de programmation");
        fieldLienProg = new SCheckBox(this, labelLienProg);

        fieldLienProg.addItemListener(this);
        fieldLienProg.addFocusListener(this);

        super.getSComponents().add(fieldLienProg);

        createPanelMaster();
    }

    @Override
    protected EditingTreat editingTreatDetail() {
        return new MCDNIDParameterTransientEditingTreat();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();

        panelInputContentCustom.add(panelId, gbc);


        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelAbsolute, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldAbsolute, gbc);


            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy++;
            panelInputContentCustom.add(labelLienProg, gbc);
            gbc.gridx++;
            panelInputContentCustom.add(fieldLienProg, gbc);


        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelAssEndIdParents, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldAssEndIdParents, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelStereotype, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldStereotype, gbc);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(panelTableComplete, gbc);

        this.add(panelInputContentCustom);

    }

    @Override
    protected void changeFieldSelectedAbsolute() {

    }

    @Override
    protected void changeFieldDeSelectedAbsolute() {
        //#MAJ 2021-05-21 Affinement MCDUnicity
        /*
        Object [] row = new Object[table.getRowCount()];
        int line = 0 ;
        while (line < table.getRowCount()){
            row = STableService.getRecord(table, line);
            if (row[OperationParamTableColumn.TYPE.getPosition()].equals(
                    MCDAssEnd.CLASSSHORTNAMEUI )){
                model.removeRow(line);
            };
            line++;
        }
        tableContentChanged();

         */
    }


    @Override
    public boolean checkDatasPreSave(SComponent sComponent) {

        boolean ok = super.checkDatasPreSave(sComponent);
        boolean notBatch = panelInput != null;
        boolean unitaire;

/*
        unitaire = notBatch  && (sComponent == fieldTarget);
        ok = checkTarget(unitaire) && ok ;

 */

        super.setPreSaveOk(ok);

        btnAdd.setEnabled(ok);

        return ok;
    }



    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);

        return ok;
    }




    protected boolean checkDetails(boolean unitaire, MVCCDElement mvccdElement) {
        boolean ok = super.checkDetails(unitaire);
        if (ok) {
            mvccdElement = null;
            if (panelInput != null) {
                mvccdElement = getEditor().getMvccdElementCrt();
            } else {
                mvccdElement = this.elementForCheckInput;
            }
        }
        return ok;
    }

    protected boolean checkDetails(boolean unitaire) {
        boolean ok = super.checkDetails(unitaire);
        if (ok) {
            MVCCDElement mvccdElement = null;
            if (panelInput != null) {
                mvccdElement = getEditor().getMvccdElementCrt();
            } else {
                mvccdElement = this.elementForCheckInput;
            }
                // Au moins un attribut obligatoire
                ok = super.checkInput(table, unitaire, MCDNIDService.checkParameters(
                        //(MCDNID) getEditor().getMvccdElementCrt(),
                        (MCDNID) mvccdElement,
                        table,
                        getContextProperty(),
                        getRowTargetProperty()));
        }
        return ok;
    }




    @Override
    protected String getContextProperty() {
        return "the.constraint.nid";
    }

    @Override
    protected String getRowContextProperty(Integer minRows) {
            if (minRows > 1) {
                return "attribute.plural";
            } else {
                return "attribute";
            }
    }

    protected String getRowTargetProperty() {
        return "attribute.redondant";
    }


    @Override
    protected String getElement(int naming) {
            return "of.nid";
    }

    @Override
    protected String getNamingAndBrothersElements(int naming) {
            if (naming == MVCCDElement.SCOPENAME) {
                return "naming.a.brother.nid";
            }
            return "naming.brother.nid";
    }




    @Override
    protected void initDatas() {
        super.initDatas();
        MCDNID forInitNID = MVCCDElementFactory.instance().createMCDNID(
                    (MCDContConstraints) getEditor().getMvccdElementParent());

        loadDatas(forInitNID);
        forInitNID.removeInParent();
        forInitNID = null;

        //fieldStereotype.setText(computeStereotype().getName());

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {

        super.loadDatas(mvccdElement);

        MCDNID mcdNID = (MCDNID) mvccdElement;
        fieldLienProg.setSelected(mcdNID.isLienProg());
    }



    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {

        super.saveDatas(mvccdElement);

            MCDNID mcdNID = (MCDNID) mvccdElement;
            if (fieldLienProg.checkIfUpdated()) {
                mcdNID.setLienProg(fieldLienProg.isSelected());
            }
    }



}
