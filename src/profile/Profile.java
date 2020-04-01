package profile;

import main.MVCCDElement;
import main.MVCCDFactory;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

public class Profile extends MVCCDElement {


    public Profile(MVCCDElement parent, String name) {
        super(parent, name);
    }

    @Override
    public String getNameTree() {
        return null;
    }

}
