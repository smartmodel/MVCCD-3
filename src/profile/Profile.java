package profile;

import main.MVCCDElement;

public class Profile extends MVCCDElement {

    private String version;

    public Profile(MVCCDElement parent, String name) {
        super(parent, name);
    }
    public Profile(MVCCDElement parent, String name, String version){
        super(parent, name);
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
