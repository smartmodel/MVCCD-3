package mpdr.tapis;

import main.MVCCDElement;
import mdr.MDRElement;
import mpdr.MPDRTable;
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


    public MPDRBoxProceduresOrFunctions getMPDRBoxProceduresOrFunctions() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRBoxProceduresOrFunctions){
                return (MPDRBoxProceduresOrFunctions) mvccdElement ;
            }
        }
        return null ;
    }


    public MPDRBoxPackages getMPDRBoxPackages() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRBoxPackages){
                return (MPDRBoxPackages) mvccdElement ;
            }
        }
        return null ;
    }


    public MPDRTable getMPDRTableAccueil (){
        return (MPDRTable) getParent();
    }

}
