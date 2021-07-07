package window.editor.mcd.relends;

import constraints.Constraint;
import constraints.ConstraintService;
import exceptions.CodeApplException;
import m.MElement;
import main.MVCCDElement;
import mcd.*;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mcd.*;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.UtilDivers;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentTable;
import utilities.window.scomponents.services.STableService;
import utilities.window.services.PanelService;
import window.editor.mcd.relation.association.AssociationEditor;
import window.editor.mcd.relation.genspec.GenSpecEditor;
import window.editor.mcd.relation.link.LinkEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RelEndsInputContent extends PanelInputContentTable {

    private EditingTreat editingTreatRelEnd = null;

    public RelEndsInputContent(RelEndsInput relEndsInput) {
        super(relEndsInput);
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

        int col;
        col = RelEndsTableColumn.NATURE.getPosition();
        table.getColumnModel().getColumn(col).setCellRenderer(table.getDefaultRenderer(ImageIcon.class));

    }

    @Override
    protected void specificInitOrLoadTable() {

        MCDContRelEnds mcdContRelEnds = (MCDContRelEnds) getEditor().getMvccdElementCrt();
        ArrayList<MCDRelEnd> mcdRelEnds = mcdContRelEnds.getMCDRelEnds();

        datas = new Object[mcdRelEnds.size()][RelEndsTableColumn.getNbColumns()];
        int line = -1;
        int col;
        for (MCDRelEnd relEnd : mcdRelEnds) {
            line++;
            putValueInRow(relEnd, datas[line]);
        }


    }

    @Override
    protected String[] specificColumnsNames() {
        return new String[]{
                RelEndsTableColumn.STEREOTYPES.getLabel(),
                RelEndsTableColumn.NATURE.getLabel(),
                RelEndsTableColumn.OPPOSITE.getLabel(),
                RelEndsTableColumn.CONSTRAINTS.getLabel()
        };
    }

    @Override
    protected boolean specificRefreshRow() {
        return true;
    }


    protected MElement actionAdd(ActionEvent e) {
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        //MElement mElement = newElement();
        MCDEntity mcdEntityContext = (MCDEntity) getEditor().getMvccdElementParent();

        String message = MessagesBuilder.getMessagesProperty("editor.relends.choice.nature");
        Object[] options = {"Annuler", "Association", "Spécialisation", "Généralisation", "Entité associative"};
        int posOption = DialogMessage.showOptions(getEditor(), message, options, JOptionPane.UNINITIALIZED_VALUE);

        if (posOption > 0) {
            if (posOption == 1) {
                MCDAssEndEditingTreat mcdAssEndEditingTreat= new MCDAssEndEditingTreat();
                mcdAssEndEditingTreat.setMcdEntityFrom(mcdEntityContext);
                editingTreatRelEnd = mcdAssEndEditingTreat;
            }

            if (posOption == 2) {
                MCDGSEndEditingTreat mcdGSEndEditingTreat= new MCDGSEndEditingTreat();
                mcdGSEndEditingTreat.setMcdEntityGen(mcdEntityContext);
                editingTreatRelEnd = mcdGSEndEditingTreat;
            }

            if (posOption == 3) {
                MCDGSEndEditingTreat mcdGSEndEditingTreat= new MCDGSEndEditingTreat();
                mcdGSEndEditingTreat.setMcdEntitySpec(mcdEntityContext);
                editingTreatRelEnd = mcdGSEndEditingTreat;
            }

            if (posOption == 4) {
                MCDLinkEndEditingTreat mcdLinkEndEditingTreat= new MCDLinkEndEditingTreat();
                mcdLinkEndEditingTreat.setMcdEntity(mcdEntityContext);
                editingTreatRelEnd = mcdLinkEndEditingTreat;
            }

        }
        return super.actionAdd(e);
    }

    @Override
    protected Object[] newRow(MElement mElement) {
        Object[] row = new Object[RelEndsTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {
        MCDRelEnd relEnd = (MCDRelEnd) mElement;
        MCDRelation relation = (MCDRelation) relEnd.getImRelation();


        ArrayList<Stereotype> stereotypes =  relEnd.getToStereotypes();
        stereotypes.addAll(relation.getToStereotypes());
        ArrayList<String> stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);

        ArrayList<Constraint> constraints =  relEnd.getToConstraints();
        constraints.addAll(relation.getToConstraints());
        ArrayList<String> constraintsUMLNames = ConstraintService.getUMLNamesByConstraints(constraints);

        int col;

        col = RelEndsTableColumn.ID.getPosition();
        row[col] = relEnd.getIdProjectElement();

        col = RelEndsTableColumn.TRANSITORY.getPosition();
        row[col] = relEnd.isTransitoryProjectElement();

        col = RelEndsTableColumn.ORDER.getPosition();
        row[col] = relEnd.getOrder();

        col = RelEndsTableColumn.NATURE.getPosition();
        //row[col] = relation.getClassShortNameUI();
        ImageIcon imageIcon = relEnd.getImageIconLong();
        if (imageIcon != null) {
            row[col] = relEnd.getImageIconLong();
        }

        col = RelEndsTableColumn.STEREOTYPES.getPosition();
        row[col] = UtilDivers.ArrayStringToString(stereotypesUMLNames, "");

        col = RelEndsTableColumn.OPPOSITE.getPosition();
        /*
        MCDElement mcdElementOpposite = relEnd.getMCDRelEndOpposite().getMcdElement();
        String nameOpposite ="";
        if ( mcdElementOpposite instanceof MCDEntity){
            nameOpposite =
        }

         */
        //row[col] = relEnd.getMCDRelEndOpposite().getMcdElement().getNameTree();
        row[col] = relEnd.getNameTree();
        //row[col] = relEnd.getMCDRelEndOpposite().getNameTree();

        col = RelEndsTableColumn.CONSTRAINTS.getPosition();
        row[col] = UtilDivers.ArrayStringToString(constraintsUMLNames, "");;

     }

    protected void actionEdit(ActionEvent e, MElement mElement){
        MCDRelEnd mcdRelEnd = (MCDRelEnd) mElement;
        fixeEditingTreatRelEnd(mcdRelEnd);

        // Update référentiel et ligne du tabeau sélectionnée
        super.actionEdit(e, mElement);

        // Modification de la ligne de l'extrémité opposée des associations réflexives
        if (mcdRelEnd != null) {
            if (mcdRelEnd.getImRelation() instanceof MCDAssociation) {
                MCDAssociation mcdAssociation = (MCDAssociation) mcdRelEnd.getImRelation();
                if (mcdAssociation.isReflexive()) {
                    MCDRelEnd mcdRelEndOpposite = mcdRelEnd.getMCDRelEndOpposite();
                    Integer indexRowOpposite = STableService.findIndexRecordById(table, mcdRelEndOpposite.getIdProjectElement());
                    if (indexRowOpposite != null) {
                        updateRow(mcdRelEndOpposite, indexRowOpposite);
                    }
                }
            }
        }
    }

    private void fixeEditingTreatRelEnd(MCDRelEnd mcdRelEnd) {
        if (mcdRelEnd.getImRelation() instanceof MCDAssociation) {
            editingTreatRelEnd = new MCDAssEndEditingTreat();
        } else if (mcdRelEnd.getImRelation() instanceof MCDGeneralization) {
            editingTreatRelEnd = new MCDGSEndEditingTreat();
        } else if (mcdRelEnd.getImRelation() instanceof MCDLink) {
            editingTreatRelEnd = new MCDLinkEndEditingTreat();
        } else {
            throw new CodeApplException("L'éditeur pour " +
                    mcdRelEnd.getNameTreePath() + " n'est pas défini.") ;
        }
    }

    protected boolean actionDelete(ActionEvent e, MElement mElement){
        // Mémorisation de l'extrémité opposée des associations réflexives
        MCDRelEnd mcdRelEnd = (MCDRelEnd) mElement;
        MCDRelEnd mcdRelEndOpposite = null;
        if (mcdRelEnd != null) {
            if (mcdRelEnd.getImRelation() instanceof MCDAssociation) {
                MCDAssociation mcdAssociation = (MCDAssociation) mcdRelEnd.getImRelation();
                if (mcdAssociation.isReflexive()) {
                    mcdRelEndOpposite = mcdRelEnd.getMCDRelEndOpposite();
                }
            }
        }

        // Suppression référentiel et ligne du tableau sélectionnée
        fixeEditingTreatRelEnd(mcdRelEnd);
        if (super.actionDelete(e, mElement)) {
            // Suppression de la ligne de l'extrémité opposée des associations réflexives
            if (mcdRelEndOpposite != null) {
                Integer indexRowOpposite = STableService.findIndexRecordById(table, mcdRelEndOpposite.getIdProjectElement());
                if (indexRowOpposite != null) {
                    model.removeRow(indexRowOpposite);
                }
            }
            return true;
        }
        return false;
    }


    @Override
    protected EditingTreat editingTreatDetail() {
        return editingTreatRelEnd;
    }

}
