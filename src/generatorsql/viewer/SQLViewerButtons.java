package generatorsql.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class SQLViewerButtons extends PanelBorderLayout {

    private SQLViewer SQLViewer;
    private SQLViewerButtonsContent content;

    public SQLViewerButtons(SQLViewer SQLViewer,
                            String borderLayoutPosition,
                            PanelBorderLayoutResizer panelBLResizer) {
        super();
        this.SQLViewer = SQLViewer;
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        content = new SQLViewerButtonsContent(this);
        super.setPanelContent(content);
    }

    public SQLViewer getSQLViewer() {
        return SQLViewer;
    }
}
