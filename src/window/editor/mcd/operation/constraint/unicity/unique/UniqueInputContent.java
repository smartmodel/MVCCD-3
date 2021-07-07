package window.editor.mcd.operation.constraint.unicity.unique;

import m.MElement;
import m.MRelationDegree;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.*;
import mcd.services.MCDUniqueService;
import project.ProjectService;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mcd.MCDUniqueParameterEditingTreat;
import repository.editingTreat.mcd.MCDUniqueParameterTransientEditingTreat;
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

public class UniqueInputContent extends UnicityInputContent {

    private JLabel labelLienProg ;
    private SCheckBox fieldLienProg ;


    public UniqueInputContent(UnicityInput uniqueInput)     {

        super(uniqueInput);
    }

    public UniqueInputContent(MVCCDElement element)     {

        super(element);
    }

    @Override
    public void createContentCustom() {

        super.createContentCustom();
        createPanelMaster();
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
        Object [] row = new Object[table.getRowCount()];
        int line = 0 ;
        while (line < table.getRowCount()){
            row = STableService.getRecord(table, line);


            if (row[OperationParamTableColumn.TYPE.getPosition()].equals(
                    MCDAssEnd.CLASSSHORTNAMEUI )){
                String subType = (String) row[OperationParamTableColumn.SUBTYPE.getPosition()];

                boolean c1 = subType.equals(MCDAssociationNature.IDCOMP.getText());
                boolean c2 = subType.equals(MCDAssociationNature.IDNATURAL.getText());
                boolean c3 = subType.equals(MCDAssociationNature.CP.getText());
                int idParam = (int) row[OperationParamTableColumn.ID.getPosition()];
                MCDParameter mcdParam = (MCDParameter) ProjectService.getProjectElementById(idParam);
                MCDAssEnd mcdAssEnd = (MCDAssEnd) mcdParam.getTarget();
                MCDAssociation  mcdAssociation = (MCDAssociation) mcdAssEnd.getMcdAssociation();
                boolean c4 = subType.equals(MCDAssociationNature.NOID.getText()) &&
                        ( mcdAssociation.getDegree() == MRelationDegree.DEGREE_MANY_MANY);

                if (  c1 || c2 || c3 || c4 ) {
                    model.removeRow(line);
                }
            };
            line++;

         }
        tableContentChanged();

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


    protected boolean checkDetails(boolean unitaire) {
        boolean ok = super.checkDetails(unitaire);
        if (ok) {
            MVCCDElement mvccdElement = null;
            if (panelInput != null) {
                mvccdElement = getEditor().getMvccdElementCrt();
            } else {
                mvccdElement = this.elementForCheckInput;
            }
            ok = super.checkInput(table, unitaire, MCDUniqueService.checkParameters(
                        //(MCDUnique) getEditor().getMvccdElementCrt(),
                        (MCDUnique) mvccdElement,
                        table,
                        getContextProperty(),
                        getRowTargetProperty()));
        }
        return ok;
    }



    @Override
    protected String getContextProperty() {
        return "the.constraint.unique";
    }

    @Override
    protected String getRowContextProperty(Integer minRows) {

            if (minRows > 1) {
                return "attribute.or.association.plural";
            } else {
                return "attribute.or.association";
            }
    }

    protected String getRowTargetProperty() {
        return "attribute.or.association.redondant";
    }



    @Override
    protected String getElement(int naming) {
        return "of.unique";
    }

    @Override
    protected String getNamingAndBrothersElements(int naming) {
            if (naming == MVCCDElement.SCOPENAME) {
                return "naming.a.sister.unique";
            }
            return "naming.sister.unique";
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        MCDUnique forInitUnique = MVCCDElementFactory.instance().createMCDUnique(
                    (MCDContConstraints) getEditor().getMvccdElementParent());

        loadDatas(forInitUnique);
        forInitUnique.removeInParent();
        forInitUnique = null;

        //fieldStereotype.setText(computeStereotype().getName());

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {

        super.loadDatas(mvccdElement);

    }


    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {

        super.saveDatas(mvccdElement);

    }

    @Override
    protected EditingTreat editingTreatDetail() {
        return new MCDUniqueParameterTransientEditingTreat();
    }

}
