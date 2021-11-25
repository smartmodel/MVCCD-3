package window.editor.mdr.mpdr.model;

import connections.ConDBMode;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelInput;

public class MPDRModelInput extends MDRModelInput {

    public MPDRModelInput(MPDRModelEditor mpdrModelEditor) {
        super(mpdrModelEditor);
    }


    @Override
    protected PanelInputContent createInputContentCustom() {

        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
            return new MPDRModelConnectionInputContent(this);
        }
        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
            return new MPDRModelConnectorInputContent(this);
        }

        return null;
    }

}
