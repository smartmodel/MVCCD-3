package window.editor.mcd.mcdtargets;

import m.MElement;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.interfaces.IMCDModel;
import mdr.MDRElement;
import mdr.MDRModel;
import preferences.Preferences;
import utilities.Trace;
import utilities.window.editor.PanelInputContentTableBasic;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MCDTargetsInputContent extends PanelInputContentTableBasic {

    private JLabel labelLastMLDRModel;
    private STextField fieldLastMLDRModel ;


    public MCDTargetsInputContent(MCDTargetsInput MCDTargetsInput)    {

        super(MCDTargetsInput);
     }

    @Override
    public void createContentCustom() {

        super.createContentCustom();

        // TODO-1 Offrir le choix du MLD-R cible et non pas seulement le dernier
        labelLastMLDRModel = new JLabel("Dernier MLD-R transformé : ");
        fieldLastMLDRModel = new STextField(this, labelLastMLDRModel);
        fieldLastMLDRModel.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        fieldLastMLDRModel.setReadOnly(true);

        super.getSComponents().add(fieldLastMLDRModel);

        createPanelMaster();
    }



    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        panelInputContentCustom.add(labelLastMLDRModel, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldLastMLDRModel,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelInputContentCustom.add(panelTableComplete, gbc);

        this.add(panelInputContentCustom);
    }


    @Override
    protected void specificColumnsDisplay() {

        int col;

        col = MCDTargetsTableColumn.ID.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(50);
        table.getColumnModel().getColumn(col).setMinWidth(50);
        table.getColumnModel().getColumn(col).setMaxWidth(50);

        col = MCDTargetsTableColumn.NAME.getPosition();
        //table.getColumnModel().getColumn(col).setPreferredWidth(400);
        //table.getColumnModel().getColumn(col).setMinWidth(400);
        //table.getColumnModel().getColumn(col).setMaxWidth(400);

        col = MCDTargetsTableColumn.MCDELEMENT.getPosition();
        //table.getColumnModel().getColumn(col).setPreferredWidth(100);
        //table.getColumnModel().getColumn(col).setMinWidth(100);
        //table.getColumnModel().getColumn(col).setMaxWidth(100);



    }

    private MDRModel getLastMDRModel(){
        MCDElement mcdElement = (MCDElement) getEditor().getMvccdElementCrt();

        IMCDElementWithTargets imcdElementWithTargets = (IMCDElementWithTargets) mcdElement;
        IMCDModel imodelAccueil = imcdElementWithTargets.getIMCDModelAccueil();
        MDRModel lastMDRModel = imodelAccueil.getLastTransformedMLDRModel();
        return lastMDRModel;
    }


    public void loadDatas(MVCCDElement mvccdElementCrt) {
        super.loadDatas(mvccdElementCrt);
        fieldLastMLDRModel.setText(getLastMDRModel().getName());

    }

    @Override
    protected void specificInitOrLoadTable() {
        MCDElement mcdElement = (MCDElement) getEditor().getMvccdElementCrt();

        ArrayList<MDRElement> mdrElements = getLastMDRModel().getMDRElementsTransformedBySource(mcdElement);

        int datasSize;
        if (mdrElements != null){
           datasSize = mdrElements.size();
        } else {datasSize = 0 ;
        }
        datas = new Object[datasSize][MCDTargetsTableColumn.getNbColumns()];
        int line=-1;
        int col;
        if (mdrElements != null) {
            for (MDRElement mdrElement : mdrElements) {
                line++;
                putValueInRow((MElement) mdrElement, datas[line]);
            }
        }
    }

    @Override
    protected String[] specificColumnsNames() {
        return  new String[]{
                MCDTargetsTableColumn.ID.getLabel(),
                MCDTargetsTableColumn.NAME.getLabel(),
                MCDTargetsTableColumn.MCDELEMENT.getLabel()
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
        return null;
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

        int col;

        col = MCDTargetsTableColumn.ID.getPosition();
        row[col] = mElement.getIdProjectElement();

        col = MCDTargetsTableColumn.NAME.getPosition();
        row[col] = mElement.getName();

        col = MCDTargetsTableColumn.MCDELEMENT.getPosition();
        row[col] = mElement.getClass().getSimpleName();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
