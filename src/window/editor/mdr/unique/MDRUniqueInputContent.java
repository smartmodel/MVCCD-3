package window.editor.mdr.unique;

import m.MElement;
import main.MVCCDElement;
import mdr.*;
import mldr.MLDRParameter;
import mldr.MLDRUnique;
import mpdr.MPDRUnique;
import preferences.Preferences;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.mcd.mcdtargets.MCDTargetsTableColumn;
import window.editor.mdr.utilities.PanelInputContentTableBasicIdMDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MDRUniqueInputContent extends PanelInputContentTableBasicIdMDR {


    private JLabel labelNature ;
    private STextField fieldNature ;
    private JLabel labelAbsolute ;
    private SCheckBox fieldAbsolute ;
    private JLabel labelFrozen ;
    private SCheckBox fieldFrozen ;


    public MDRUniqueInputContent(MDRUniqueInput MDRUniqueInput)     {
        super(MDRUniqueInput);
    }

    public MDRUniqueInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        labelNature = new JLabel("Nature ");
        fieldNature = new STextField(this, labelNature);
        fieldNature.setPreferredSize(new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT));
        fieldNature.setReadOnly(true);

        labelAbsolute = new JLabel("{absolute} ");
        fieldAbsolute = new SCheckBox(this, labelAbsolute);
        fieldAbsolute.setReadOnly(true);

        labelFrozen = new JLabel("{frozen} ");
        fieldFrozen = new SCheckBox(this, labelFrozen);
        fieldFrozen.setReadOnly(true);

        createPanelMaster();
    }



    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        panelInputContentCustom.add(labelNature, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldNature, gbc);

        gbc.gridx++;
        panelInputContentCustom.add(labelAbsolute, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldAbsolute, gbc);

        gbc.gridx++;
        panelInputContentCustom.add(labelFrozen, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldFrozen, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 6;
        panelInputContentCustom.add(panelTableComplete, gbc);

        this.add(panelInputContentCustom);

    }


    @Override
    protected void specificColumnsDisplay() {
        int col;

        int widthColName = 400;

        col = MDRUniqueTableColumn.COLUMNNAME.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMinWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMaxWidth(widthColName);

        col = MDRUniqueTableColumn.PARAMTYPE.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMinWidth(widthColName);
        //table.getColumnModel().getColumn(col).setMaxWidth(widthColName);

    }

    @Override
    protected void specificInitOrLoadTable() {
        MDRUnique mdrUnique = (MDRUnique) getEditor().getMvccdElementCrt();

        ArrayList<MDRParameter> mdrParameters = mdrUnique.getMDRParameters();

        int datasSize;
        if (mdrParameters != null){
            datasSize = mdrParameters.size();
        } else {
            datasSize = 0 ;
        }
        datas = new Object[datasSize][MCDTargetsTableColumn.getNbColumns()];
        int line=-1;
        int col;
        if (mdrParameters != null) {
            for (MDRParameter mdrParameter :mdrParameters) {
                line++;
                putValueInRow(mdrParameter, datas[line]);
            }
        }

    }

    @Override
    protected String[] specificColumnsNames() {
        return  new String[]{
                MDRUniqueTableColumn.COLUMNNAME.getLabel(),
                MDRUniqueTableColumn.PARAMTYPE.getLabel()
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
        MDRParameter mdrParameter = (MDRParameter) mElement;

        int col;

        col = MDRUniqueTableColumn.COLUMNNAME.getPosition();
        row[col] = mdrParameter.getName();

        col = MDRUniqueTableColumn.PARAMTYPE.getPosition();
        if (getEditor().getMvccdElementCrt() instanceof MLDRUnique){
            row[col] = ((MLDRParameter) mdrParameter).getTargetMCDType();
        }

        if (getEditor().getMvccdElementCrt() instanceof MPDRUnique){
            row[col] = "Code à écrire !!!";
        }
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRUnique mdrUnique = (MDRUnique) mvccdElementCrt;
        super.loadDatas(mdrUnique);
        fieldNature.setText(mdrUnique.getMdrUniqueNature().getText());
        fieldAbsolute.setSelected(mdrUnique.isAbsolute());
        fieldFrozen.setSelected(mdrUnique.isFrozen());
     }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
