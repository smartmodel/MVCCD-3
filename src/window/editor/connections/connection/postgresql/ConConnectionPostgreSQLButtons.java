package window.editor.connections.connection.postgresql;


import utilities.window.editor.PanelButtonsContent;
import window.editor.connections.connection.ConConnectionButtons;

public class ConConnectionPostgreSQLButtons extends ConConnectionButtons {


    public ConConnectionPostgreSQLButtons(ConConnectionPostgreSQLEditor conResourcePostgreSQLEditor) {
        super(conResourcePostgreSQLEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new ConConnectionPostgreSQLButtonsContent(this);
    }
}
