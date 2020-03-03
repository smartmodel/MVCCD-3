package window.editor.attributes;

import constraints.Constraint;
import constraints.ConstraintService;
import main.MVCCDElement;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import utilities.UtilDivers;
import utilities.window.ReadTableModel;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AttributesInputContent extends PanelInputContent {

    private JPanel panel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JButton buttonAdd;
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

        MCDContAttributes mcdContAttributes = (MCDContAttributes) getEditor().getMvccdElement();
        ArrayList<MCDAttribute> mcdAttributes= mcdContAttributes.getMCDAttributes();

        Object[][] data = new Object[mcdAttributes.size()][AttributesTableColumn.getNbColumns()];
        int line=-1;
        int col;
        for (MCDAttribute attribute:mcdAttributes){
            line++;


            ArrayList<Stereotype> stereotypes =  attribute.getToStereotypes();
            ArrayList<String> stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);

            ArrayList<Constraint> constraints =  attribute.getToConstraints();
            ArrayList<String> constraintsUMLNames = ConstraintService.getUMLNamesByConstraints(constraints);

            col = AttributesTableColumn.ID.getPosition();
            data[line][col] = attribute.getId();

            col = AttributesTableColumn.STEREOTYPES.getPosition();
            data[line][col] = UtilDivers.ArrayStringToString(stereotypesUMLNames, "");

            col = AttributesTableColumn.NAME.getPosition();
            data[line][col] = attribute.getName();

            col = AttributesTableColumn.DATATYPE.getPosition();
            data[line][col] = "";

            col = AttributesTableColumn.DATASIZE.getPosition();
            data[line][col] = attribute.getSize();

            col = AttributesTableColumn.DATASCALE.getPosition();
            data[line][col] = attribute.getScale();

            col = AttributesTableColumn.UPPERCASE.getPosition();
            data[line][col] = attribute.isUppercase();

            col = AttributesTableColumn.CONSTRAINTS.getPosition();
            data[line][col] = UtilDivers.ArrayStringToString(constraintsUMLNames, "");;

            col = AttributesTableColumn.DERIVED.getPosition();
            data[line][col] = attribute.isDerived();

            col = AttributesTableColumn.DEFAULTVALUE.getPosition();
            String defaultValue = "";
            if (attribute.getInitValue() != null){
                defaultValue = attribute.getInitValue();
            }
            if (attribute.getDerivedValue() != null){
                defaultValue = attribute.getDerivedValue();
            }
            data[line][col] = defaultValue;
        }



        model = new ReadTableModel(data, columnNames);

        table = new JTable();
        table.setModel(model);
        //table.setEnabled(false);

        table.setRowHeight(Preferences.EDITOR_TABLE_ROW_HEIGHT);

        //TODO-2 PAS Reprendre le formattage numérique
        //table.setDefaultEditor(Integer.class,  new IntegerEditor(0, 100000));


        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //table.getColumnModel().setColumnSelectionAllowed(false);

        col = AttributesTableColumn.ID.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(20);
        table.getColumnModel().getColumn(col).setMinWidth(20);
        table.getColumnModel().getColumn(col).setMaxWidth(20);

        col = AttributesTableColumn.STEREOTYPES.getPosition();
        //table.getColumnModel().getColumn(col).setPreferredWidth(100);
        table.getColumnModel().getColumn(col).setPreferredWidth(50);
        table.getColumnModel().getColumn(col).setCellEditor(new AttributeStereotypesEditorForJTable(table));

        col = AttributesTableColumn.NAME.getPosition();
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
            public void mouseClicked(MouseEvent arg0) {
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                //component.getHandler().datasInEdition(table.isEditing());
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                //component.getHandler().datasInEdition(table.isEditing());
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }
        });


    }

    private void  makeButtons(){
        buttonAdd = new JButton("Ajouter");
        buttonAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                model.addRow(newRow());

            }
        });

        buttonRemove = new JButton("Supprimer");
        buttonRemove.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual >= 0){
                    model.removeRow(posActual);
                }
            }
        });

        buttonUp = new JButton("^");
        buttonUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int posActual = table.getSelectedRow();
                if (posActual > 0){
                    model.moveRow(posActual, posActual, posActual-1);
                    table.setRowSelectionInterval(posActual-1, posActual-1);
                }

            }
        });

        buttonDown = new JButton("v");
        buttonDown.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int posActual = table.getSelectedRow();
                int posFuture;
                if (posActual < table.getRowCount()-1){
                    //table.setEnabled(true);
                    model.moveRow(posActual, posActual, posActual+1);
                    table.setRowSelectionInterval(posActual+1, posActual+1);
                    //table.setEnabled(false);
                }

            }
        });


    }



    protected void changeField(DocumentEvent e) {
    }



    @Override
    protected void changeField(ItemEvent e) {

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    protected boolean checkDatas(){
            return true;
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
    }

    @Override
    protected void initDatas(MVCCDElement mvccdElement) {
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
    }



    @Override
    public boolean checkDatasPreSave() {
        return true;
    }

    private void enabledContent() {
    }

    private Object[] newRow(){

        Object[] dataEmpty = new Object [AttributesTableColumn.getNbColumns()];
        return dataEmpty;
    }
}
