package window.editor.mcd.operation.constraint.constraints;

import constraints.Constraint;
import constraints.ConstraintService;
import exceptions.CodeApplException;
import m.MElement;
import main.MVCCDElement;
import mcd.*;
import messages.MessagesBuilder;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mcd.*;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.UtilDivers;
import utilities.window.DialogMessage;
import utilities.window.editor.PanelInputContentTable;
import utilities.window.services.PanelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ConstraintsInputContent extends PanelInputContentTable {

    private EditingTreat editingTreatUnicity = null;

    public ConstraintsInputContent(ConstraintsInput constraintsInput)    {

        super(constraintsInput);
     }

    @Override
    public void createContentCustom() {

        super.createContentCustom();

        createPanelMaster();
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        panelInputContentCustom.add(panelTableComplete, gbc);

        this.add(panelInputContentCustom);
    }


    @Override
    protected void specificColumnsDisplay() {


        table.getColumnModel().getColumn(ConstraintsTableColumn.STEREOTYPES.getPosition()).setPreferredWidth(
                Preferences.EDITOR_CONSTRAINTS_STEREO_WIDTH);
        table.getColumnModel().getColumn(ConstraintsTableColumn.STEREOTYPES.getPosition()).setMinWidth(
                Preferences.EDITOR_CONSTRAINTS_STEREO_WIDTH);
        table.getColumnModel().getColumn(ConstraintsTableColumn.STEREOTYPES.getPosition()).setMaxWidth(
                Preferences.EDITOR_CONSTRAINTS_STEREO_WIDTH);


        table.getColumnModel().getColumn(ConstraintsTableColumn.NAME.getPosition()).setPreferredWidth(
                Preferences.EDITOR_CONSTRAINTS_NAME_WIDTH);
        table.getColumnModel().getColumn(ConstraintsTableColumn.NAME.getPosition()).setMinWidth(
                Preferences.EDITOR_CONSTRAINTS_NAME_WIDTH);
        table.getColumnModel().getColumn(ConstraintsTableColumn.NAME.getPosition()).setMaxWidth(
                Preferences.EDITOR_CONSTRAINTS_NAME_WIDTH);

        table.getColumnModel().getColumn(ConstraintsTableColumn.CONSTRAINTS.getPosition()).setPreferredWidth(
                Preferences.EDITOR_CONSTRAINTS_UMLCONSTRAINTS_WIDTH);
        table.getColumnModel().getColumn(ConstraintsTableColumn.CONSTRAINTS.getPosition()).setMinWidth(
                Preferences.EDITOR_CONSTRAINTS_UMLCONSTRAINTS_WIDTH);
        table.getColumnModel().getColumn(ConstraintsTableColumn.CONSTRAINTS.getPosition()).setMaxWidth(
                Preferences.EDITOR_CONSTRAINTS_UMLCONSTRAINTS_WIDTH);


    }

    @Override
    protected void specificInitOrLoadTable() {

        MCDContConstraints mcdContConstraints = (MCDContConstraints) getEditor().getMvccdElementCrt();
        ArrayList<MCDConstraint> mcdConstraints = mcdContConstraints.getMCDConstraintsSortDefault();

        datas = new Object[mcdConstraints.size()][ConstraintsTableColumn.getNbColumns()];
        int line=-1;
        int col;
        for (MCDConstraint constraint:mcdConstraints){
            line++;
            putValueInRow(constraint, datas[line]);
        }


    }

    @Override
    protected String[] specificColumnsNames() {
        return  new String[]{
                ConstraintsTableColumn.STEREOTYPES.getLabel(),
                //ConstraintsTableColumn.NATURE.getLabel(),
                ConstraintsTableColumn.NAME.getLabel(),
                ConstraintsTableColumn.PARAMETERS.getLabel(),
                ConstraintsTableColumn.CONSTRAINTS.getLabel()
        };
    }

    @Override
    protected boolean specificRefreshRow() {
        return true;
    }

    protected MElement actionAdd(ActionEvent e) {
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save

        String message = MessagesBuilder.getMessagesProperty("editor.constaints.choice.nature");
        //Object[] options = {"Annuler", "Id. naturel", "Unique", "Logique"};
        Object[] options = {"Annuler", "Id. naturel", "Unique"};
        int posOption = DialogMessage.showOptions(getEditor(), message, options, JOptionPane.UNINITIALIZED_VALUE);

        if (posOption > 0 ) {
            if (posOption == 1) {
                MCDNIDEditingTreat mcdNIDEditingTreat = new MCDNIDEditingTreat();
                editingTreatUnicity = mcdNIDEditingTreat;
            }
            if (posOption == 2) {
                MCDUniqueEditingTreat mcdUniqueEditingTreat = new MCDUniqueEditingTreat();
                editingTreatUnicity = mcdUniqueEditingTreat;
            }
        }
        return super.actionAdd(e);
    }

    protected void actionEdit(ActionEvent e, MElement mElement){
        MCDUnicity mcdUnicity = (MCDUnicity) mElement;
        fixeEditingTreatRelEnd(mcdUnicity);

        // Update référentiel et ligne du tabeau sélectionnée
        super.actionEdit(e, mElement);
    }

    protected boolean actionDelete(ActionEvent e, MElement mElement){
         MCDUnicity mcdUnicity = (MCDUnicity) mElement;

        // Suppression référentiel et ligne du tableau sélectionnée
        fixeEditingTreatRelEnd(mcdUnicity);
        return super.actionDelete(e, mcdUnicity) ;
    }


    private void fixeEditingTreatRelEnd(MCDUnicity mcdUnicity) {
        if (mcdUnicity instanceof MCDNID) {
            editingTreatUnicity = new MCDNIDEditingTreat();
        } else if (mcdUnicity instanceof MCDUnique) {
            editingTreatUnicity = new MCDUniqueEditingTreat();
        } else  {
            throw new CodeApplException("L'éditeur pour " +
                    mcdUnicity.getNameTreePath() + " n'est pas défini.") ;
        }
    }

    @Override
    protected EditingTreat editingTreatDetail() {
        return editingTreatUnicity;
    }

    @Override
    protected Object[] newRow(MElement mElement) {
        Object[] row = new Object [ConstraintsTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {

        MCDConstraint constraint = (MCDConstraint) mElement;

        int col;

        col = ConstraintsTableColumn.ID.getPosition();
        row[col] = constraint.getIdProjectElement();

        col = ConstraintsTableColumn.TRANSITORY.getPosition();
        row[col] = constraint.isTransitoryProjectElement();

        col = ConstraintsTableColumn.ORDER.getPosition();
        row[col] = constraint.getOrder();

        //col = ConstraintsTableColumn.NATURE.getPosition();
        //row[col] = constraint.getClassShortNameUI();

        col = ConstraintsTableColumn.STEREOTYPES.getPosition();
        row[col] = constraint.getStereotypesInLine();

        col = ConstraintsTableColumn.NAME.getPosition();
        row[col] = constraint.getName();

        col = ConstraintsTableColumn.PARAMETERS.getPosition();
        row[col] = constraint.getParametersNameAsStr();

        col = ConstraintsTableColumn.CONSTRAINTS.getPosition();
        row[col] = constraint.getConstraintsInLine();

     }


}
