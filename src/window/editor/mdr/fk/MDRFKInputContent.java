package window.editor.mdr.fk;

import m.MElement;
import m.MRelEndMulti;
import main.MVCCDElement;
import mdr.MDRColumn;
import mdr.MDRFK;
import mdr.MDRRelationFK;
import preferences.Preferences;
import utilities.window.services.PanelService;
import window.editor.mcd.mcdtargets.MCDTargetsTableColumn;
import window.editor.mdr.utilities.PanelInputContentTableBasicIdMDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MDRFKInputContent extends PanelInputContentTableBasicIdMDR {


    private JLabel labelPK ;
    private JTextField fieldPK ;

    private JLabel labelDeleteCascade ;
    private JCheckBox fieldDeleteCascade ;

    private JPanel panelRelationFK ;

    private JPanel panelRelationFKEndRef ;
    private JLabel labelEndRefMultiStd;
    private JTextField fieldEndRefMultiStd ;

    
    private JPanel panelRelationFKEndOwner;
    private JLabel labelEndOwnerMultiStd;
    private JTextField fieldEndOwnerMultiStd ;
    private JLabel labelEndOwnerMultiCustom;
    private JTextField fieldEndOwnerMultiCustom ;



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
        fieldPK = new JTextField();
        fieldPK.setPreferredSize(new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT));
        fieldPK.setEnabled(false);


        labelDeleteCascade = new JLabel("{deleteCascade}");
        fieldDeleteCascade = new JCheckBox();
        fieldDeleteCascade.setEnabled(false);

        panelRelationFK = new JPanel();

        panelRelationFKEndRef = new JPanel();
        labelEndRefMultiStd = new JLabel("Multiplicité");
        fieldEndRefMultiStd = new JTextField();
        fieldEndRefMultiStd.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldEndRefMultiStd.setEnabled(false);

        panelRelationFKEndOwner = new JPanel();
        labelEndOwnerMultiStd = new JLabel("Multiplicité standard");
        fieldEndOwnerMultiStd = new JTextField();
        fieldEndOwnerMultiStd.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldEndOwnerMultiStd.setEnabled(false);
        labelEndOwnerMultiCustom = new JLabel("Multiplicité personnalisée");
        fieldEndOwnerMultiCustom = new JTextField();
        fieldEndOwnerMultiCustom.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldEndOwnerMultiCustom.setEnabled(false);

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
        panelInputContentCustom.add(labelDeleteCascade, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldDeleteCascade, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelInputContentCustom.add(panelTableComplete, gbc);

        createPanelRelationFK();
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(panelRelationFK, gbc);

        this.add(panelInputContentCustom);

    }

    private void createPanelRelationFK() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelRelationFK, "Relation graphique FK");

        createPanelRelationFKEndRef();
        gbc.gridx = 0;
        gbc.gridy++;
        panelRelationFK.add(panelRelationFKEndRef, gbc);

        createPanelRelationFKEndOwner();
        gbc.gridx++;
        panelRelationFK.add(panelRelationFKEndOwner, gbc);

    }

    private void createPanelRelationFKEndRef() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelRelationFKEndRef, "Extr. référence (PK)");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelRelationFKEndRef.add(labelEndRefMultiStd, gbc);
        gbc.gridx++;
        panelRelationFKEndRef.add(fieldEndRefMultiStd, gbc);

    }

    private void createPanelRelationFKEndOwner() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelRelationFKEndOwner, "Extr. propriétaire (FK)");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelRelationFKEndOwner.add(labelEndOwnerMultiStd, gbc);
        gbc.gridx++;
        panelRelationFKEndOwner.add(fieldEndOwnerMultiStd, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelRelationFKEndOwner.add(labelEndOwnerMultiCustom, gbc);
        gbc.gridx++;
        panelRelationFKEndOwner.add(fieldEndOwnerMultiCustom, gbc);

    }


    @Override
    protected void specificColumnsDisplay() {
        int col;

        int widthColName = 300;

        col = MDRFKTableColumn.COLUMNFK.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(widthColName);

        col = MDRFKTableColumn.COLUMNPK.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(widthColName);

        col = MDRFKTableColumn.LEVELREFPK.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(200);

    }

    @Override
    protected void specificInitOrLoadTable() {
        MDRFK mdrFK = (MDRFK) getEditor().getMvccdElementCrt();

        ArrayList<MDRColumn> mdrColumns =mdrFK.getMDRColumnsSortDefault();

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
                MDRFKTableColumn.COLUMNPK.getLabel(),
                MDRFKTableColumn.LEVELREFPK.getLabel()
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

        col = MDRFKTableColumn.LEVELREFPK.getPosition();
        String level = "";
        //if(mdrColumn.isPk() && mdrColumn.isFk()){
        if(mdrColumn.isFk()){
                level = ""+ mdrColumn.getLevelForPK();
                if (mdrColumn.getMDRColumnPK().isFk())
                    level += " in parent FK-" + mdrColumn.getMDRColumnPK().getFk().getIndice() ;
        }
        row[col] = level;
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRFK mdrFK = (MDRFK) mvccdElementCrt;
        super.loadDatas(mdrFK);
        fieldPK.setText(mdrFK.getMdrPK().getName());
        fieldDeleteCascade.setSelected(mdrFK.isDeleteCascade());
        loadRelationFK(mdrFK.getMDRRelationFK());
    }

    private void loadRelationFK(MDRRelationFK mdrRelationFK) {
        MRelEndMulti ownerMultiStd = MRelEndMulti.findByTwoValues(
                mdrRelationFK.getEndChild().getMultiMinStd(),
                mdrRelationFK.getEndChild().getMultiMaxStd());
        fieldEndOwnerMultiStd.setText(ownerMultiStd.getText());
        MRelEndMulti refMultiStd = MRelEndMulti.findByTwoValues(
                mdrRelationFK.getEndParent().getMultiMinStd(),
                mdrRelationFK.getEndParent().getMultiMaxStd());
        fieldEndRefMultiStd.setText(refMultiStd.getText());
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
