package generatorsql.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class SQLViewerParameters extends PanelBorderLayout {

    private SQLViewer SQLViewer;
    private SQLViewerParametersContent sqlViewerParametersContent;

    public SQLViewerParameters(SQLViewer SQLViewer,
                               String borderLayoutPosition,
                               PanelBorderLayoutResizer panelBLResizer) {
        super();
        this.SQLViewer = SQLViewer;
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        sqlViewerParametersContent = new SQLViewerParametersContent(this);
        super.setPanelContent(sqlViewerParametersContent);
    }

    public SQLViewer getSQLViewer() {
        return SQLViewer;
    }

    public SQLViewerParametersContent getSqlViewerParametersContent() {
        return sqlViewerParametersContent;
    }
}
