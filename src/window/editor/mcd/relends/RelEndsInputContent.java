package window.editor.mcd.relends;

import constraints.Constraint;
import constraints.ConstraintService;
import m.MElement;
import main.MVCCDElement;
import mcd.*;
import messages.MessagesBuilder;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDGeneralizationEditingTreat;
import repository.editingTreat.mcd.MCDLinkEditingTreat;
import repository.editingTreat.mcd.MCDRelationEditingTreat;
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


    @Override
    protected MElement newElement() {
        MVCCDElement newElement = null;
        MCDEntity mcdEntityContext = (MCDEntity) getEditor().getMvccdElementParent();
        MCDContRelations mcdContRelations = (MCDContRelations) mcdEntityContext.getParent().getBrotherByClassName(MCDContRelations.class.getName());

        String message = MessagesBuilder.getMessagesProperty("editor.relends.choice.nature");
        Object[] options = {"Annuler", "Association", "Spécialisation", "Généralisation", "Entité associative"};
        int posOption = DialogMessage.showOptions(getEditor(), message, options, JOptionPane.UNINITIALIZED_VALUE);

        if (posOption > 0) {
            if (posOption == 1) {
                newElement = newAssociation(mcdEntityContext, mcdContRelations);
            }
            if (posOption == 2) {
                newElement = newSpecialization(mcdEntityContext, mcdContRelations);
            }
            if (posOption == 3) {
                newElement = newGeneralization(mcdEntityContext, mcdContRelations);
            }
            if (posOption == 4) {
                newElement = newLink(mcdEntityContext, mcdContRelations);
            }
        }

         return (MElement) newElement;
    }


    private MVCCDElement newAssociation(MCDEntity mcdEntityContext, MCDContRelations mcdContRelations) {
        MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
        MCDAssociation newMCDAssociation = mcdAssociationEditingTreat.treatNew(getEditor(), mcdContRelations,
                mcdEntityContext, null, null, true);
        if (newMCDAssociation != null){
            return newMCDAssociation.getFrom();  // Extrémité mcdEntityContext
        } else {
            return null;
        }
    }

    private MVCCDElement newSpecialization(MCDEntity mcdEntityContext, MCDContRelations mcdContRelations) {
        MCDGeneralizationEditingTreat mcdGeneralizationEditingTreat = new MCDGeneralizationEditingTreat();
        MCDGeneralization mcdGeneralization = mcdGeneralizationEditingTreat.treatNew(getEditor(), mcdContRelations,
                mcdEntityContext, null,  true);
        if (mcdGeneralization != null){
            return mcdGeneralization.getGen();  // Extrémité mcdEntityContext
        } else {
            return null;
        }
    }

    private MVCCDElement newGeneralization(MCDEntity mcdEntityContext, MCDContRelations mcdContRelations) {
        MCDGeneralizationEditingTreat mcdGeneralizationEditingTreat = new MCDGeneralizationEditingTreat();
        MCDGeneralization mcdGeneralization = mcdGeneralizationEditingTreat.treatNew(getEditor(), mcdContRelations,
                 null,  mcdEntityContext, true);
        if (mcdGeneralization != null){
            return mcdGeneralization.getSpec();  // Extrémité mcdEntityContext
        } else {
            return null;
        }
    }

    private MVCCDElement newLink(MCDEntity mcdEntityContext, MCDContRelations mcdContRelations) {
        MCDLinkEditingTreat mcdLinkEditingTreat = new MCDLinkEditingTreat();
        MCDLink newMCDLink = mcdLinkEditingTreat.treatNew(getEditor(), mcdContRelations,
                mcdEntityContext, null,  true);
        if (newMCDLink != null) {
            return newMCDLink.getEndEntity();  // Extrémité mcdEntityContext
        } else {
            return null;
        }
    }


    @Override
    protected Object[] newRow(MElement mElement) {
        Object[] row = new Object[RelEndsTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    @Override
    protected void updateElement(MElement mElement) {
        MCDRelEnd mcdRelEnd = (MCDRelEnd) mElement;
        DialogEditor fen = null;
        if (mcdRelEnd.getMcdRelation() instanceof MCDAssociation) {
            fen = new AssociationEditor(getEditor(), (MCDContRelations) mcdRelEnd.getMcdRelation().getParent(),
                    (MCDAssociation) mcdRelEnd.getMcdRelation(),
                    DialogEditor.UPDATE,
                    new MCDAssociationEditingTreat());
        }
        if (mcdRelEnd.getMcdRelation() instanceof MCDGeneralization) {
            fen = new GenSpecEditor(getEditor(), (MCDContRelations) mcdRelEnd.getMcdRelation().getParent(),
                    (MCDGeneralization) mcdRelEnd.getMcdRelation(),
                    DialogEditor.UPDATE,
                    new MCDGeneralizationEditingTreat());
        }
        if (mcdRelEnd.getMcdRelation() instanceof MCDLink) {
            fen = new LinkEditor(getEditor(), (MCDContRelations) mcdRelEnd.getMcdRelation().getParent(),
                    (MCDLink) mcdRelEnd.getMcdRelation(),
                    DialogEditor.UPDATE,
                    new MCDLinkEditingTreat());
        }

        fen.setVisible(true);
    }



    @Override
    protected boolean deleteElement(MElement mElement) {
        MCDRelation mcdRelation = ((MCDRelEnd) mElement).getMcdRelation();
        return new MCDRelationEditingTreat().treatDelete(getEditor(), mcdRelation );
    }

    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {
        MCDRelEnd relEnd = (MCDRelEnd) mElement;
        MCDRelation relation = relEnd.getMcdRelation();


        ArrayList<Stereotype> stereotypes =  relEnd.getToStereotypes();
        stereotypes.addAll(relation.getToStereotypes());
        ArrayList<String> stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);

        ArrayList<Constraint> constraints =  relEnd.getToConstraints();
        constraints.addAll(relation.getToConstraints());
        ArrayList<String> constraintsUMLNames = ConstraintService.getUMLNamesByConstraints(constraints);

        int col;

        col = RelEndsTableColumn.ID.getPosition();
        row[col] = relEnd.getId();

        col = RelEndsTableColumn.TRANSITORY.getPosition();
        row[col] = relEnd.isTransitory();

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
        // Update référentiel et ligne du tabeau sélectionnée
        super.actionEdit(e, mElement);

        // Modification de la ligne de l'extrémité opposée des associations réflexives
        MCDRelEnd mcdRelEnd = (MCDRelEnd) mElement;
        if (mcdRelEnd != null) {
            if (mcdRelEnd.getMcdRelation() instanceof MCDAssociation) {
                MCDAssociation mcdAssociation = (MCDAssociation) mcdRelEnd.getMcdRelation();
                if (mcdAssociation.isReflexive()) {
                    MCDRelEnd mcdRelEndOpposite = mcdRelEnd.getMCDRelEndOpposite();
                    Integer indexRowOpposite = STableService.findIndexRecordById(table, mcdRelEndOpposite.getId());
                    if (indexRowOpposite != null) {
                        updateRow(mcdRelEndOpposite, indexRowOpposite);
                    }
                }
            }
        }
    }

    protected boolean actionDelete(ActionEvent e, MElement mElement){
        // Mémorisation l'extrémité opposée des associations réflexives
        MCDRelEnd mcdRelEnd = (MCDRelEnd) mElement;
        MCDRelEnd mcdRelEndOpposite = null;
        if (mcdRelEnd != null) {
            if (mcdRelEnd.getMcdRelation() instanceof MCDAssociation) {
                MCDAssociation mcdAssociation = (MCDAssociation) mcdRelEnd.getMcdRelation();
                if (mcdAssociation.isReflexive()) {
                    mcdRelEndOpposite = mcdRelEnd.getMCDRelEndOpposite();
                }
            }
        }

        // Suppression référentiel et ligne du tabeau sélectionnée
        if (super.actionDelete(e, mElement)) {
            // Suppression de la ligne de l'extrémité opposée des associations réflexives
            if (mcdRelEndOpposite != null) {
                Integer indexRowOpposite = STableService.findIndexRecordById(table, mcdRelEndOpposite.getId());
                if (indexRowOpposite != null) {
                    model.removeRow(indexRowOpposite);
                }
            }
            return true;
        }
        return false;
    }


}
