package generatorsql.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class SQLViewerButtons extends PanelBorderLayout {

    private SQLViewer SQLViewer;
    private SQLViewerButtonsContent sqlViewerButtonsContent;

    public SQLViewerButtons(SQLViewer SQLViewer,
                            String borderLayoutPosition,
                            PanelBorderLayoutResizer panelBLResizer) {
        super();
        this.SQLViewer = SQLViewer;
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        sqlViewerButtonsContent = new SQLViewerButtonsContent(this);
        super.setPanelContent(sqlViewerButtonsContent);
    }

    public SQLViewer getSQLViewer() {
        return SQLViewer;
    }

    public SQLViewerButtonsContent getSqlViewerButtonsContent() {
        return sqlViewerButtonsContent;
    }
}
