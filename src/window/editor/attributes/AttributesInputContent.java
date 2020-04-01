package window.editor.attributes;

import constraints.Constraint;
import constraints.ConstraintService;
import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElement;
import main.MVCCDElementService;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import newEditor.DialogEditor;
import newEditor.PanelInputContent;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import project.ProjectElement;
import project.ProjectManager;
import project.ProjectService;
import repository.RepositoryService;
import repository.editingTreat.MCDAttributeEditingTreat;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.UtilDivers;
import utilities.window.ReadTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AttributesInputContent extends PanelInputContent {

    private JPanel panel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JButton buttonAdd;
    private JButton buttonAddAID;
    private JButton buttonEdit;
    private JButton buttonRemove;
    private JButton buttonUp;
    private JButton buttonDown;


    public AttributesInputContent(AttributesInput attributesInput)    {
        super(attributesInput);
        attributesInput.setPanelContent(this);
        createContent();
        super.addContent(panel, false);
        super.initOrLoadDatas();
        enabledContent();
     }




    private void createContent() {
        makeTable();
        makeButtons();
        makeLayout();
    }

    private void  makeLayout(){
        JScrollPane panelTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel panelMove = new JPanel();
        panelMove.setLayout(new BoxLayout(panelMove, BoxLayout.Y_AXIS));
        panelMove.add(Box.createVerticalGlue());
        panelMove.add(buttonUp);
        panelMove.add(buttonDown);
        panelMove.add(Box.createVerticalGlue());


        JPanel panelButtons= new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        //panelButtons.setMaximumSize(ApplicationManager.instance().getViewManager().getScaledDimension(new Dimension(800, 10)));

        panelButtons.add(Box.createHorizontalGlue());
        panelButtons.add(buttonAdd);
        panelButtons.add(buttonEdit);
        panelButtons.add(buttonRemove);
        panelButtons.add(Box.createHorizontalGlue());


        BorderLayout bl = new BorderLayout(5,5);
        panel.setLayout(bl);
        panel.add(panelTable, BorderLayout.CENTER);
        panel.add(panelButtons, BorderLayout.SOUTH);
        panel.add(panelMove, BorderLayout.EAST);

    }

    private void makeTable(){
        String[] columnNames = {
                AttributesTableColumn.ID.getLabel(),
                AttributesTableColumn.ORDER.getLabel(),
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

        MCDContAttributes mcdContAttributes = (MCDContAttributes) getEditor().getMvccdElementCrt();
        ArrayList<MCDAttribute> mcdAttributes= mcdContAttributes.getMCDAttributes();

        Object[][] data = new Object[mcdAttributes.size()][AttributesTableColumn.getNbColumns()];
        int line=-1;
        int col;
        for (MCDAttribute attribute:mcdAttributes){
            line++;
            putValueInRow(attribute, data[line]);
        }



        model = new ReadTableModel(data, columnNames);

        table = new JTable();
        table.setModel(model);


        table.setRowHeight(Preferences.EDITOR_TABLE_ROW_HEIGHT);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //table.getColumnModel().setColumnSelectionAllowed(false);

        int sizeDebug = 0;
        if (PreferencesManager.instance().preferences().isDEBUG() &&
            PreferencesManager.instance().preferences().isDEBUG_SHOW_TABLE_COL_HIDDEN()){
            sizeDebug = 20;
        }
        
        col = AttributesTableColumn.ID.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(col).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(col).setMaxWidth(sizeDebug);

        col = AttributesTableColumn.ORDER.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(col).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(col).setMaxWidth(sizeDebug);

        col = AttributesTableColumn.STEREOTYPES.getPosition();
        //table.getColumnModel().getColumn(col).setPreferredWidth(100);
        table.getColumnModel().getColumn(col).setPreferredWidth(50);
        table.getColumnModel().getColumn(col).setCellEditor(new AttributeStereotypesEditorForJTable(table));

        col = AttributesTableColumn.NAME.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(100);
        table.getColumnModel().getColumn(col).setMinWidth(100);

        col = AttributesTableColumn.DATATYPE.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(100);
        table.getColumnModel().getColumn(col).setMinWidth(100);

        col = AttributesTableColumn.DATASIZE.getPosition();
        table.getColumnModel().getColumn(col).setMinWidth(40);
        table.getColumnModel().getColumn(col).setMaxWidth(40);


        col = AttributesTableColumn.DATASCALE.getPosition();
        table.getColumnModel().getColumn(col).setMinWidth(60);
        table.getColumnModel().getColumn(col).setMaxWidth(60);

        col = AttributesTableColumn.UPPERCASE.getPosition();
        table.getColumnModel().getColumn(col).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(col).setCellEditor(table.getDefaultEditor(Boolean.class));
        table.getColumnModel().getColumn(col).setMinWidth(70);
        table.getColumnModel().getColumn(col).setMaxWidth(70);

        col = AttributesTableColumn.DERIVED.getPosition();
        table.getColumnModel().getColumn(col).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(col).setCellEditor(table.getDefaultEditor(Boolean.class));
        table.getColumnModel().getColumn(col).setMinWidth(50);
        table.getColumnModel().getColumn(col).setMaxWidth(50);

        col = AttributesTableColumn.DEFAULTVALUE.getPosition();
        //table.getColumnModel().getColumn(col).setPreferredWidth(100);
        table.getColumnModel().getColumn(col).setPreferredWidth(50);


        table.setFillsViewportHeight(true);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                // ce ne sont pas les boutons standards mais ceux ceux spécifiques à la table
                enabledContent();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    

    private void  makeButtons(){
        buttonAdd = new JButton("Ajouter");
        buttonAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                MCDAttribute mcdAttribute = MCDAttributeEditingTreat.treatNew( getEditor(),
                        (MCDContAttributes) getEditor().getMvccdElementCrt());

                if (mcdAttribute != null) {
                    Object[] row = newRow(mcdAttribute);
                    model.addRow(row);
                    table.setRowSelectionInterval(table.getRowCount()-1 , table.getRowCount()-1);
                    enabledContent();
                }
            }
        });

        buttonRemove = new JButton("Supprimer");
        buttonRemove.setEnabled(false);
        buttonRemove.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual >= 0){

                    //TODO-0 Faire appel treatDelete
                    DefaultMutableTreeNode nodeAttribute = ProjectService.getNodeById(posActual);
                    DefaultTreeModel treeModel =  MVCCDManager.instance().getWinRepositoryContent().getTree().getTreeModel();
                    treeModel.removeNodeFromParent(nodeAttribute);
                    model.removeRow(posActual);
                    enabledContent();
                }
            }
        });

        buttonEdit = new JButton("Editer");
        buttonEdit.setEnabled(false);
        buttonEdit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual >= 0){
                    int posId = AttributesTableColumn.ID.getPosition();
                    Integer idActual = (Integer) table.getModel().getValueAt(posActual, posId);
                    MCDAttribute mcdAttributeActual = (MCDAttribute) ProjectService.getElementById(idActual);
                    MCDAttributeEditingTreat.treatUpdate( getEditor(), mcdAttributeActual);

                    updateRow(mcdAttributeActual, table.getSelectedRow());
                    enabledContent();
                }
            }
        });

        buttonUp = new JButton("^");
        buttonUp.setEnabled(false);
        buttonUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int posActual = table.getSelectedRow();
                if (posActual > 0){
                    model.moveRow(posActual, posActual, posActual-1);
                    table.setRowSelectionInterval(posActual-1, posActual-1);
                    permuteOrder(posActual, posActual - 1);
                }
                enabledContent();

            }
        });

        buttonDown = new JButton("v");
        buttonDown.setEnabled(false);
        buttonDown.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual < table.getRowCount()-1){
                    model.moveRow(posActual, posActual, posActual+1);
                    table.setRowSelectionInterval(posActual+1, posActual+1);
                    permuteOrder(posActual, posActual + 1);
                }
                enabledContent();

            }


        });


    }



    protected void changeField(DocumentEvent e) {
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }




    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }


    @Override
    public boolean checkDatasPreSave(boolean unitaire) {

        return true;
    }


    protected boolean checkDatas(){
            return true;
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
    }



    @Override
    protected void initDatas() {
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
    }



    private void enabledContent() {
        int pos = table.getSelectedRow();
        if (pos >= 0){
            buttonRemove.setEnabled(true);
            buttonEdit.setEnabled(true);
            if (pos > 0){
                buttonUp.setEnabled(true);
            } else {
                buttonUp.setEnabled(false);
            }
            if (pos == table.getRowCount()-1){
                buttonDown.setEnabled(false);
            } else{
                buttonDown.setEnabled(true);
            }
        } else {
            buttonRemove.setEnabled(false);
            buttonEdit.setEnabled(false);
            buttonUp.setEnabled(false);
            buttonDown.setEnabled(false);
        }
    }

    private void updateRow(MCDAttribute mcdAttributeSelected, int selectedRow) {

        Object[] row = new Object [AttributesTableColumn.getNbColumns()];
        putValueInRow(mcdAttributeSelected, row);
        UtilDivers.putValueRowInTable(table, selectedRow, row);

    }

    private Object[] newRow(MCDAttribute mcdAttribute){

        Object[] row = new Object [AttributesTableColumn.getNbColumns()];
        putValueInRow(mcdAttribute, row);
        return row;
    }

    private void putValueInRow(MCDAttribute attribute, Object[] row) {
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


    private void permuteOrder(int posActual, int posNew) {

        int posOrder = AttributesTableColumn.ORDER.getPosition();
        Integer orderActual = (Integer) table.getModel().getValueAt(posActual, posOrder);
        Integer orderNew = (Integer) table.getModel().getValueAt(posNew, posOrder);

        int posId = AttributesTableColumn.ID.getPosition();
        Integer idActual = (Integer) table.getModel().getValueAt(posActual, posId);
        Integer idOther = (Integer) table.getModel().getValueAt(posNew, posId);

        System.out.println("idActual"   +idActual);
        System.out.println("idNew"   +idOther);

        // Permutation dans la table
        table.getModel().setValueAt(orderNew, posActual, posOrder);
        table.getModel().setValueAt(orderActual, posNew, posOrder);

        // Permutation dans les 2 instances de descendants de ProjectElement
        updateOrderINProjectElement(idActual, orderNew);
        updateOrderINProjectElement(idOther, orderActual);

        MVCCDManager.instance().setDatasProjectChanged(true);

        // Mise à jour de l'affichage du référentiel
        swapNodes(idActual, idOther);



    }


    private void updateOrderINProjectElement(Integer id, Integer order) {
        /*
        Project project = MVCCDManager.instance().getProject();
        ProjectElement projectElement = project.getElementById(id);

         */
        ProjectElement projectElement = ProjectService.getElementById(id);
        projectElement.setOrder(order);
    }

    private void swapNodes(Integer idA, Integer idB) {

        DefaultMutableTreeNode nodeParent =  ProjectService.getNodeById(
                ((ProjectElement) getEditor().getMvccdElementCrt()).getId());

        DefaultMutableTreeNode nodeA = RepositoryService.instance().getNodeInChildsByIdElement(
               nodeParent, idA);
        int indexA = nodeParent.getIndex(nodeA);


        DefaultMutableTreeNode nodeB = RepositoryService.instance().getNodeInChildsByIdElement(
                nodeParent, idB);
        int indexB = nodeParent.getIndex(nodeB);

        nodeParent.insert(nodeA, indexB);
        nodeParent.insert(nodeB, indexA);

        MVCCDManager.instance().showNewNodeInRepository(nodeB);

    }

}
