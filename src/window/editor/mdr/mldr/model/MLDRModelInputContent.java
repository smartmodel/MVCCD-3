package window.editor.mdr.mldr.model;

import utilities.window.services.PanelService;
import window.editor.mdr.model.MDRModelInputContent;

import java.awt.*;

public class MLDRModelInputContent extends MDRModelInputContent {

    public MLDRModelInputContent(MLDRModelInput mldrModelInput) {
        super(mldrModelInput);
    }


    @Override
    public void createContentCustom() {
        super.createContentCustom();
        createPanelMaster();
    }

    protected void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        super.createPanelMaster(gbc);

        this.add(panelInputContentCustom);
    }

}
