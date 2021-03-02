package window.editor.mcd.operation.constraint.constraints;

import constraints.Constraint;
import constraints.ConstraintService;
import m.MElement;
import main.MVCCDElement;
import mcd.MCDConstraint;
import mcd.MCDContConstraints;
import mcd.MCDNID;
import mcd.MCDUnique;
import messages.MessagesBuilder;
import preferences.Preferences;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import repository.editingTreat.mcd.MCDUniqueEditingTreat;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.UtilDivers;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentTable;
import utilities.window.services.PanelService;
import window.editor.mcd.operation.constraint.unicity.nid.NIDEditor;
import window.editor.mcd.operation.constraint.unicity.unique.UniqueEditor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConstraintsInputContent extends PanelInputContentTable {


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
        ArrayList<MCDConstraint> mcdConstraints = mcdContConstraints.getMCDConstraints();

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


    @Override
    protected MElement newElement() {
        DialogEditor fen = null;
        MVCCDElement newElement = null;
        MCDContConstraints mcdContConstraints = (MCDContConstraints) getEditor().getMvccdElementCrt();

        String message = MessagesBuilder.getMessagesProperty("editor.constaints.choice.nature");
        //Object[] options = {"Annuler", "Id. naturel", "Unique", "Logique"};
        Object[] options = {"Annuler", "Id. naturel", "Unique"};
        int posOption = DialogMessage.showOptions(getEditor(), message, options, JOptionPane.UNINITIALIZED_VALUE);

        if (posOption > 0 ) {
            if (posOption == 1) {
                fen = new NIDEditor(getEditor(), mcdContConstraints, null,
                        DialogEditor.NEW, new MCDNIDEditingTreat());
            }

            if (posOption == 2) {
                fen = new UniqueEditor(getEditor(), mcdContConstraints, null,
                        DialogEditor.NEW, new MCDUniqueEditingTreat());
            }
            fen.setVisible(true);
            newElement = fen.getMvccdElementNew();
        }

        return (MElement) newElement;
    }

    @Override
    protected Object[] newRow(MElement mElement) {
        Object[] row = new Object [ConstraintsTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    @Override
    protected void updateElement(MElement mElement) {
        DialogEditor fen = null;
        if (mElement instanceof MCDNID) {
            fen = new NIDEditor(getEditor(), (MCDContConstraints) mElement.getParent(),
                    (MCDNID) mElement,
                    DialogEditor.UPDATE,
                    new MCDNIDEditingTreat());
        }
        if (mElement instanceof MCDUnique) {
            fen = new UniqueEditor(getEditor(), (MCDContConstraints) mElement.getParent(),
                    (MCDUnique) mElement,
                    DialogEditor.UPDATE,
                    new MCDUniqueEditingTreat());
        }
        fen.setVisible(true);
    }

    @Override
    protected boolean deleteElement(MElement mElement) {
        return new MCDNIDEditingTreat().treatDelete(getEditor(), mElement);
    }

    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {

        MCDConstraint constraint = (MCDConstraint) mElement;

        ArrayList<Stereotype> stereotypes =  constraint.getToStereotypes();
        ArrayList<String> stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);

        ArrayList<Constraint> constraints =  constraint.getToConstraints();

        ArrayList<String> constraintsUMLNames = ConstraintService.getUMLNamesByConstraints(constraints);

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
        row[col] = UtilDivers.ArrayStringToString(stereotypesUMLNames, "");

        col = ConstraintsTableColumn.NAME.getPosition();
        row[col] = constraint.getName();

        col = ConstraintsTableColumn.PARAMETERS.getPosition();
        row[col] = constraint.getParametersNameAsStr();

        col = ConstraintsTableColumn.CONSTRAINTS.getPosition();
        row[col] = UtilDivers.ArrayStringToString(constraintsUMLNames, "");;

     }


}
