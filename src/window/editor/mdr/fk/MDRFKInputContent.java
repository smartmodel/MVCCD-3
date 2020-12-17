package window.editor.mdr.fk;

import main.MVCCDElement;
import mdr.MDRFK;
import mdr.MDRPK;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import java.awt.*;

public class MDRFKInputContent extends PanelInputContentIdMDR {



    public MDRFKInputContent(MDRFKInput MDRFKInput)     {
        super(MDRFKInput);
    }

    public MDRFKInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();
        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);


        /*

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelSource,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldSource, gbc);

         */

        this.add(panelInputContentCustom);
    }




    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRFK mdrFK = (MDRFK) mvccdElementCrt;
        super.loadDatas(mdrFK);
    }



}
