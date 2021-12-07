package resultat.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class ResultatViewerQuittance extends PanelBorderLayout {

    private ResultatViewerQuittanceContent resultatViewerQuittanceContent;

    public ResultatViewerQuittance(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        resultatViewerQuittanceContent = new ResultatViewerQuittanceContent(this);
        super.setPanelContent(resultatViewerQuittanceContent);

    }


    public ResultatViewerQuittanceContent getResultatViewerQuittanceContent() {
        return resultatViewerQuittanceContent;
    }
}
