package window.editor.attributes;

import constraints.Constraint;
import constraints.ConstraintService;
import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDParameterService;
import repository.editingTreat.mcd.MCDNIDParameterEditingTreat;
import repository.editingTreat.mcd.MCDUniqueParameterEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import project.ProjectService;
import repository.RepositoryService;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.UtilDivers;
import utilities.window.ReadTableModel;
import utilities.window.editor.PanelInputContentIdTable;
import utilities.window.editor.PanelInputContentTable;
import utilities.window.scomponents.SCheckBox;
import utilities.window.services.ComponentService;
import utilities.window.services.PanelService;
import window.editor.attribute.AttributeEditor;
import window.editor.attribute.AttributeTransientEditor;
import window.editor.operation.OperationParamTableColumn;
import window.editor.operation.constraint.unique.UniqueEditor;
import window.editor.operation.parameter.ParameterEditor;
import window.editor.operation.parameter.ParameterTransientEditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AttributesInputContent extends PanelInputContentTable {


    public AttributesInputContent(AttributesInput attributesInput)    {

        super(attributesInput);
     }

    @Override
    public void createContentCustom() {

        super.createContentCustom();

        createPanelMaster();
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
    protected Object[] getNewRow(MElement mElement) {
        Object[] row = new Object [AttributesTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    @Override
    protected MElement getNewElement() {
        DialogEditor fen = null;
        MCDContAttributes mcdContAttribute = (MCDContAttributes) getEditor().getMvccdElementCrt();

        fen = new AttributeEditor(getEditor(), mcdContAttribute, null,
                    DialogEditor.NEW, new MCDAttributeEditingTreat());

        fen.setVisible(true);
        MVCCDElement newElement = fen.getMvccdElementNew();
        return (MElement) newElement;
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
        row[col] = attribute.getId();

        col = AttributesTableColumn.TRANSITORY.getPosition();
        row[col] = attribute.isTransitory();

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


}
