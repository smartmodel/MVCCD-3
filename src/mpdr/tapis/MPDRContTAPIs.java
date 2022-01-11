package mpdr.tapis;

import main.MVCCDElement;
import mdr.MDRElement;
import project.ProjectElement;

public class MPDRContTAPIs extends MDRElement {

    private static final long serialVersionUID = 1000;

    public MPDRContTAPIs(ProjectElement parent, String name) {
        super(parent, name);
    }



    public MPDRTriggers getMPDRTriggers() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRTriggers){
                return (MPDRTriggers) mvccdElement ;
            }
        }
        return null ;
    }

}
