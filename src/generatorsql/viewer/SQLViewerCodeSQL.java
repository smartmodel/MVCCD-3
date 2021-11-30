package generatorsql.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class SQLViewerCodeSQL extends PanelBorderLayout {

    private SQLViewerCodeSQLContent sqlViewerCodeSQLContent;

    public SQLViewerCodeSQL(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        sqlViewerCodeSQLContent = new SQLViewerCodeSQLContent(this);
        super.setPanelContent(sqlViewerCodeSQLContent);

    }


    public SQLViewerCodeSQLContent getSqlViewerCodeSQLContent() {
        return sqlViewerCodeSQLContent;
    }
}
