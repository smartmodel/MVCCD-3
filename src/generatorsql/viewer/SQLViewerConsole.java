package generatorsql.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class SQLViewerConsole extends PanelBorderLayout  {

    private SQLViewerConsoleContent sqlViewerConsoleContent;
    private SQLViewer sqlViewer;

    public SQLViewerConsole(SQLViewer sqlViewer, String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        this.sqlViewer = sqlViewer;
        startLayout();

        sqlViewerConsoleContent = new SQLViewerConsoleContent(this);
        super.setPanelContent(sqlViewerConsoleContent);

    }

    public SQLViewer getSqlViewer() {
        return sqlViewer;
    }

    public SQLViewerConsoleContent getSqlViewerConsoleContent() {
        return sqlViewerConsoleContent;
    }


  }
