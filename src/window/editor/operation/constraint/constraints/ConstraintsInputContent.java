package window.editor.operation.constraint.constraints;

import constraints.Constraint;
import constraints.ConstraintService;
import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import m.MElement;
import main.MVCCDElement;
import mcd.*;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.ProjectService;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import repository.editingTreat.mcd.MCDUniqueEditingTreat;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;
import utilities.UtilDivers;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentTable;
import utilities.window.scomponents.services.STableService;
import utilities.window.services.PanelService;
import window.editor.attribute.AttributeEditor;
import window.editor.operation.constraint.unique.UniqueEditor;

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
                ConstraintsTableColumn.NATURE.getLabel(),
                ConstraintsTableColumn.NAME.getLabel(),
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
                fen = new UniqueEditor(getEditor(), mcdContConstraints, null,
                        DialogEditor.NEW,
                        UniqueEditor.NID, new MCDNIDEditingTreat());
            }

            if (posOption == 2) {
                fen = new UniqueEditor(getEditor(), mcdContConstraints, null,
                        DialogEditor.NEW,
                        UniqueEditor.UNIQUE, new MCDUniqueEditingTreat());
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
            fen = new UniqueEditor(getEditor(), (MCDContConstraints) mElement.getParent(),
                    (MCDNID) mElement,
                    DialogEditor.UPDATE,
                    UniqueEditor.NID,
                    new MCDNIDEditingTreat());
        }
        if (mElement instanceof MCDUnique) {
            fen = new UniqueEditor(getEditor(), (MCDContConstraints) mElement.getParent(),
                    (MCDUnique) mElement,
                    DialogEditor.UPDATE,
                    UniqueEditor.UNIQUE,
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
        row[col] = constraint.getId();

        col = ConstraintsTableColumn.TRANSITORY.getPosition();
        row[col] = constraint.isTransitory();

        col = ConstraintsTableColumn.ORDER.getPosition();
        row[col] = constraint.getOrder();

        col = ConstraintsTableColumn.NATURE.getPosition();
        row[col] = constraint.getClassShortNameUI();

        col = ConstraintsTableColumn.STEREOTYPES.getPosition();
        row[col] = UtilDivers.ArrayStringToString(stereotypesUMLNames, "");

        col = ConstraintsTableColumn.NAME.getPosition();
        row[col] = constraint.getName();

        col = ConstraintsTableColumn.CONSTRAINTS.getPosition();
        row[col] = UtilDivers.ArrayStringToString(constraintsUMLNames, "");;

     }


}
