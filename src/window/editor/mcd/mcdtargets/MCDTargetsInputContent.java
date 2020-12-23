package window.editor.mcd.mcdtargets;

import m.MElement;
import main.MVCCDElement;
import mcd.interfaces.IMCDElementWithTargets;
import mldr.interfaces.IMLDRElement;
import utilities.window.editor.PanelInputContentTableBasic;
import utilities.window.services.PanelService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MCDTargetsInputContent extends PanelInputContentTableBasic {


    public MCDTargetsInputContent(MCDTargetsInput MCDTargetsInput)    {

        super(MCDTargetsInput);
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

        this.add(panelInputContentCustom);
    }


    @Override
    protected void specificColumnsDisplay() {

        int col;

        col = MCDTargetsTableColumn.ID.getPosition();
        col = MCDTargetsTableColumn.NAME.getPosition();
        col = MCDTargetsTableColumn.MCDELEMENT.getPosition();


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

    @Override
    protected void specificInitOrLoadTable() {

        IMCDElementWithTargets imcdElementWithTargets = (IMCDElementWithTargets) getEditor().getMvccdElementCrt();
        ArrayList<IMLDRElement> imldrElements = imcdElementWithTargets.getImldrElementTargets();

        int datasSize;
        if (imldrElements != null){
           datasSize = imldrElements.size();
        } else {datasSize = 0 ;
        }
        datas = new Object[datasSize][MCDTargetsTableColumn.getNbColumns()];
        int line=-1;
        int col;
        if (imldrElements != null) {
            for (IMLDRElement imldrElement : imldrElements) {
                line++;
                putValueInRow((MElement) imldrElement, datas[line]);
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
        row[col] = mElement.getId();

        col = MCDTargetsTableColumn.NAME.getPosition();
        row[col] = mElement.getName();

        col = MCDTargetsTableColumn.MCDELEMENT.getPosition();
        row[col] = mElement.getClass().getSimpleName();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
