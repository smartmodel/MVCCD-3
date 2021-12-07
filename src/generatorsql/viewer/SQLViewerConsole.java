package generatorsql.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class SQLViewerConsole extends PanelBorderLayout  {

    private SQLViewerConsoleContent sqlViewerConsoleContent;

    public SQLViewerConsole(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        sqlViewerConsoleContent = new SQLViewerConsoleContent(this);
        super.setPanelContent(sqlViewerConsoleContent);

    }


    public SQLViewerConsoleContent getSqlViewerConsoleContent() {
        return sqlViewerConsoleContent;
    }

  }
