package window.editor.mcd.attributes;

import constraints.Constraint;
import constraints.ConstraintService;
import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import exceptions.service.ExceptionService;
import m.MElement;
import main.MVCCDElement;
import mcd.*;
import mcd.services.MCDNIDService;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.UtilDivers;
import utilities.window.editor.PanelInputContentTable;
import utilities.window.services.PanelService;

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
    protected Object[] newRow(MElement mElement) {
        Object[] row = new Object [AttributesTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }


    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {
        MCDAttribute attribute = (MCDAttribute) mElement;

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
        //row[col] = UtilDivers.arrayStringToString(stereotypesUMLNames, "");
        row[col] = attribute.getStereotypesInLine();

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
        row[col] =attribute.getConstraintsInLine();

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

    @Override
    protected EditingTreat editingTreatDetail() {
        return new MCDAttributeEditingTreat();
    }

}
