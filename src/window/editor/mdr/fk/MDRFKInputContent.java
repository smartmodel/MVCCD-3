package window.editor.mdr.fk;

import m.MElement;
import main.MVCCDElement;
import mdr.MDRColumn;
import mdr.MDRFK;
import preferences.Preferences;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.mcd.mcdtargets.MCDTargetsTableColumn;
import window.editor.mdr.utilities.PanelInputContentTableBasicIdMDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MDRFKInputContent extends PanelInputContentTableBasicIdMDR {


    private JLabel labelPK ;
    private STextField fieldPK ;


    public MDRFKInputContent(MDRFKInput MDRFKInput)     {
        super(MDRFKInput);
    }

    public MDRFKInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        labelPK = new JLabel("Table de référence (PK)");
        fieldPK = new STextField(this, labelPK);
        fieldPK.setPreferredSize(new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT));
        fieldPK.setReadOnly(true);

        createPanelMaster();
    }



    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        panelInputContentCustom.add(labelPK, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldPK, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelInputContentCustom.add(panelTableComplete, gbc);

        this.add(panelInputContentCustom);

    }


    @Override
    protected void specificColumnsDisplay() {
        int col;

        int widthColName = 400;

        col = MDRFKTableColumn.COLUMNFK.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMinWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMaxWidth(widthColName);

        col = MDRFKTableColumn.COLUMNPK.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMinWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMaxWidth(widthColName);

    }

    @Override
    protected void specificInitOrLoadTable() {
        MDRFK mdrFK = (MDRFK) getEditor().getMvccdElementCrt();

        ArrayList<MDRColumn> mdrColumns =mdrFK.getMDRColumns();

        int datasSize;
        if (mdrColumns != null){
            datasSize = mdrColumns.size();
        } else {
            datasSize = 0 ;
        }
        datas = new Object[datasSize][MCDTargetsTableColumn.getNbColumns()];
        int line=-1;
        int col;
        if (mdrColumns != null) {
            for (MDRColumn mdrColumn : mdrColumns) {
                line++;
                putValueInRow(mdrColumn, datas[line]);
            }
        }

    }

    @Override
    protected String[] specificColumnsNames() {
        return  new String[]{
                MDRFKTableColumn.COLUMNFK.getLabel(),
                MDRFKTableColumn.COLUMNPK.getLabel()
        };
    }

    @Override
    protected boolean specificRefreshRow() {
        return false;
    }

    @Override
    protected MElement newElement() {
        return null;
    }

    @Override
    protected Object[] newRow(MElement mElement) {
        return new Object[0];
    }

    @Override
    protected void updateElement(MElement mElement) {

    }

    @Override
    protected boolean deleteElement(MElement mElement) {
        return false;
    }

    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {
        MDRColumn mdrColumn = (MDRColumn) mElement;

        int col;

        col = MDRFKTableColumn.COLUMNFK.getPosition();
        row[col] = mdrColumn.getName();

        col = MDRFKTableColumn.COLUMNPK.getPosition();
        row[col] = mdrColumn.getMDRColumnPK().getName();
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRFK mdrFK = (MDRFK) mvccdElementCrt;
        super.loadDatas(mdrFK);
        fieldPK.setText(mdrFK.getMdrPK().getName());
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
