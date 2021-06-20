package window.editor.mcd.attributes;

import constraints.Constraint;
import constraints.ConstraintService;
import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import exceptions.service.ExceptionService;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.*;
import mcd.services.MCDNIDService;
import preferences.Preferences;
import project.ProjectElement;
import project.ProjectService;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.Trace;
import utilities.UtilDivers;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentTable;
import utilities.window.services.PanelService;
import window.editor.mcd.attribute.AttributeEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AttributesInputContent extends PanelInputContentTable {

    protected JButton btnCreateNID1;


    public AttributesInputContent(AttributesInput attributesInput)    {

        super(attributesInput);
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

        btnCreateNID1 = new JButton("Création de l'identifiant naturel");
        btnCreateNID1.setEnabled(false);
        btnCreateNID1.addActionListener(this);

        panelButtons.add(btnCreateNID1);

        this.add(panelInputContentCustom);
    }


    @Override
    protected void specificColumnsDisplay() {

        int col;
        col = AttributesTableColumn.UPPERCASE.getPosition();
        table.getColumnModel().getColumn(col).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        //table.getColumnModel().getColumn(col).setCellEditor(table.getDefaultEditor(Boolean.class));

        col = AttributesTableColumn.DERIVED.getPosition();
        table.getColumnModel().getColumn(col).setCellRenderer(table.getDefaultRenderer(Boolean.class));

    }

    @Override
    protected void specificInitOrLoadTable() {

        MCDContAttributes mcdContAttributes = (MCDContAttributes) getEditor().getMvccdElementCrt();
        ArrayList<MCDAttribute> mcdAttributes= mcdContAttributes.getMCDAttributes();

        datas = new Object[mcdAttributes.size()][AttributesTableColumn.getNbColumns()];
        int line=-1;
        int col;
        for (MCDAttribute attribute:mcdAttributes){
            line++;
            putValueInRow(attribute, datas[line]);
        }


    }

    @Override
    protected String[] specificColumnsNames() {
        return  new String[]{
                AttributesTableColumn.STEREOTYPES.getLabel(),
                AttributesTableColumn.NAME.getLabel(),
                AttributesTableColumn.DATATYPE.getLabel(),
                AttributesTableColumn.DATASIZE.getLabel(),
                AttributesTableColumn.DATASCALE.getLabel(),
                AttributesTableColumn.UPPERCASE.getLabel(),
                AttributesTableColumn.CONSTRAINTS.getLabel(),
                AttributesTableColumn.DERIVED.getLabel(),
                AttributesTableColumn.DEFAULTVALUE.getLabel()
        };
    }


    @Override
    protected boolean specificRefreshRow() {
        return false;
    }


    @Override
    protected MElement newElement() {
        DialogEditor fen = null;
        MCDContAttributes mcdContAttribute = (MCDContAttributes) getEditor().getMvccdElementCrt();

        fen = new AttributeEditor(getEditor(), mcdContAttribute, null,
                DialogEditor.NEW, new MCDAttributeEditingTreat());

        fen.setVisible(true);
        MVCCDElement newElement = fen.getMvccdElementNew();
        return (MElement) newElement;
    }



    @Override
    protected Object[] newRow(MElement mElement) {
        Object[] row = new Object [AttributesTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    @Override
    protected void updateElement(MElement mElement) {
        DialogEditor fen = new AttributeEditor(getEditor(), (MCDContAttributes) mElement.getParent(),
                (MCDAttribute) mElement,
                DialogEditor.UPDATE, new MCDAttributeEditingTreat());
        fen.setVisible(true);
    }

    @Override
    protected boolean deleteElement(MElement mElement) {
        return new MCDAttributeEditingTreat().treatDelete(getEditor(), mElement);
    }

    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {
        MCDAttribute attribute = (MCDAttribute) mElement;
        ArrayList<Stereotype> stereotypes =  attribute.getToStereotypes();
        ArrayList<String> stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);

        ArrayList<Constraint> constraints =  attribute.getToConstraints();
        ArrayList<String> constraintsUMLNames = ConstraintService.getUMLNamesByConstraints(constraints);

        String textForDatatype = "";
        if(attribute.getDatatypeLienProg() != null) {
            MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(attribute.getDatatypeLienProg());
            textForDatatype = mcdDatatype.getName();
        }

        int col;

        col = AttributesTableColumn.ID.getPosition();
        row[col] = attribute.getIdProjectElement();

        col = AttributesTableColumn.TRANSITORY.getPosition();
        row[col] = attribute.isTransitoryProjectElement();

        col = AttributesTableColumn.ORDER.getPosition();
        row[col] = attribute.getOrder();

        col = AttributesTableColumn.STEREOTYPES.getPosition();
        row[col] = UtilDivers.ArrayStringToString(stereotypesUMLNames, "");

        col = AttributesTableColumn.NAME.getPosition();
        row[col] = attribute.getName();


        col = AttributesTableColumn.DATATYPE.getPosition();
        row[col] = textForDatatype;

        col = AttributesTableColumn.DATASIZE.getPosition();
        row[col] = attribute.getSize();

        col = AttributesTableColumn.DATASCALE.getPosition();
        row[col] = attribute.getScale();

        col = AttributesTableColumn.UPPERCASE.getPosition();
        //table.getColumnModel().getColumn(col).setCellEditor(table.getDefaultEditor(Boolean.class));
        row[col] = attribute.isUppercase();

        col = AttributesTableColumn.CONSTRAINTS.getPosition();
        row[col] = UtilDivers.ArrayStringToString(constraintsUMLNames, "");;

        col = AttributesTableColumn.DERIVED.getPosition();
        row[col] = attribute.isDerived();

        col = AttributesTableColumn.DEFAULTVALUE.getPosition();
        String defaultValue = "";
        if (attribute.getInitValue() != null){
            defaultValue = attribute.getInitValue();
        }
        if (attribute.getDerivedValue() != null){
            defaultValue = attribute.getDerivedValue();
        }
        row[col] = defaultValue;
    }

    protected void enabledContent() {
        int pos = table.getSelectedRow();
        if (pos >= 0){
            btnCreateNID1.setEnabled(nid1Authorized(pos));
        } else {
            btnCreateNID1.setEnabled(false);
        }
        super.enabledContent();
    }

    private boolean nid1Authorized (int pos) {
        MCDAttribute mcdAttribute = (MCDAttribute) getMElementSelected();

        return MCDNIDService.attributeCandidateForNID1(mcdAttribute);
    }

    public void actionPerformed(ActionEvent e) {
        String propertyAction = "";
        try {
            Object source = e.getSource();
            MCDAttribute mcdAttribute = (MCDAttribute) super.getMElementSelected();

            if (source == btnCreateNID1) {
                propertyAction = "editor.table.attributes.exception.createNID1";
                if (MCDNIDService.confirmCreateNID1FromAttribute(getEditor().getOwner(), mcdAttribute)) {
                    refreshRow(table.getSelectedRow());
                }
            }
            super.actionPerformed(e);
        } catch (Exception exception){
            ExceptionService.exceptionUnhandled(exception, getEditor(),
                    getEditor().getMvccdElementCrt(),
                    "editor.table.attributes.exception",
                    propertyAction);

        }
    }

}
