package mpdr.tapis;

import main.MVCCDElement;
import mdr.MDRElement;
import project.ProjectElement;

public class MPDRContTAPIs extends MDRElement {

    private static final long serialVersionUID = 1000;

    public MPDRContTAPIs(ProjectElement parent, String name) {
        super(parent, name);
    }



    public MPDRBoxTriggers getMPDRBoxTriggers() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRBoxTriggers){
                return (MPDRBoxTriggers) mvccdElement ;
            }
        }
        return null ;
    }

}
