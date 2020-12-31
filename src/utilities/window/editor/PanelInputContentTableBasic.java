package utilities.window.editor;

import m.MElement;
import main.MVCCDElement;
import mcd.MCDConstraint;
import preferences.Preferences;
import project.ProjectService;
import utilities.UtilDivers;
import utilities.window.ReadTableModel;
import utilities.window.scomponents.STable;
import utilities.window.scomponents.services.STableService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public abstract class PanelInputContentTableBasic extends PanelInputContent
        implements ActionListener {


    protected STable table;
    String[] columnsNames;
    protected DefaultTableModel model;
    protected Object[][] datas;
    protected Object[][] oldDatas;


    protected JPanel panelTableComplete = new JPanel();
    protected JScrollPane panelTable;


    public PanelInputContentTableBasic(PanelInput panelInput) {
        super(panelInput);
    }


    @Override
    public void createContentCustom() {

        makeTable();
        makeLayout();
    }

    protected void makeTable() {
        columnsNames = specificColumnsNames();

        specificInitOrLoadTable();
        oldDatas = datas;

        table = new STable(this);
        model = new ReadTableModel(datas, columnsNames);

        table.setModel(model);


        table.setRowHeight(Preferences.EDITOR_TABLE_ROW_HEIGHT);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        specificColumnsDisplay();


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


    protected abstract void specificColumnsDisplay();

    protected abstract void specificInitOrLoadTable();

    protected abstract String[] specificColumnsNames();

    protected abstract boolean specificRefreshRow();

/*
    protected void refreshRow(int selectedRow){
        int idElementSelected = (int) table.getValueAt(selectedRow, STableService.IDINDEX);
        MElement mElementSelected = (MElement) ProjectService.getElementById(idElementSelected);

        MCDConstraint constraint = (MCDConstraint) mElementSelected;

        Object[] row = STableService.getRecord(table, selectedRow);

        putValueInRow(mElementSelected, row);
        UtilDivers.putValueRowInTable(table, selectedRow, row);
    };

 */


    protected abstract MElement newElement();

    protected abstract Object[] newRow(MElement mElement);

    protected abstract void updateElement(MElement mElement);

    protected void updateRow(MElement mElement, int selectedRow) {

        Object[] row = STableService.getRecord(table, selectedRow);
        putValueInRow(mElement, row);
        UtilDivers.putValueRowInTable(table, selectedRow, row);
    }

    protected abstract boolean deleteElement(MElement mElement);


    private void  makeLayout(){
        panelTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        BorderLayout bl = new BorderLayout(5,5);
        panelTableComplete.setLayout(bl);
        panelTableComplete.add(panelTable, BorderLayout.CENTER);


    }


    protected abstract void putValueInRow(MElement mElement, Object[] row);


    @Override
    protected void enabledContent() {


    }


    // Pour éviter de surcharger les descendants
    @Override
    protected boolean changeField(DocumentEvent e) {
        return false;
    }

    // Pour éviter de surcharger les descendants
    @Override
    protected void changeFieldSelected(ItemEvent e) {
    }

    // Pour éviter de surcharger les descendants
    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    // Pour éviter de surcharger les descendants

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {

    }


    // Pour éviter de surcharger les descendants
    @Override
    protected void initDatas() {

    }

    // Pour éviter de surcharger les descendants
    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {

    }


    public Dimension resizeContent() {
        //TODO-1 A voir plus en détail surtout la hauteur

        int widthGap =  (Preferences.JPANEL_HGAP *4) ;
        Dimension newDimension = super.resizeContent();
        panelTable.setPreferredSize(
                new Dimension((int) newDimension.getWidth() -widthGap, 200));
        panelTable.setSize(
                new Dimension((int) newDimension.getWidth() -widthGap, 200));
        return newDimension;
    }



    private MElement getMElementSelected() {
        int posSelected = table.getSelectedRow();
        if (posSelected >= 0) {
            int idElementSelected = (int) table.getValueAt(posSelected, STableService.IDINDEX);

            MElement mElementSelected = (MElement) ProjectService.getElementById(idElementSelected);
            return mElementSelected;
        }
        return null;
    }


}
