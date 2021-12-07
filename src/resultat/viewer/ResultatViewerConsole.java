package resultat.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class ResultatViewerConsole extends PanelBorderLayout  {

    private ResultatViewerConsoleContent resultatViewerConsoleContent;

    public ResultatViewerConsole(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        resultatViewerConsoleContent = new ResultatViewerConsoleContent(this);
        super.setPanelContent(resultatViewerConsoleContent);

    }


    public ResultatViewerConsoleContent getResultatViewerConsoleContent() {
        return resultatViewerConsoleContent;
    }

  }
