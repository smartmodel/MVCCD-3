package delete;

import main.MVCCDElement;
import main.MVCCDManager;
import utilities.Trace;

public class Delete {

    public static MVCCDElement deleteMVCCDElement (MVCCDElement mvccdElement){
        MVCCDManager.instance().removeMVCCDElementInRepository(mvccdElement, mvccdElement.getParent());
        mvccdElement.removeInParent();
        mvccdElement = null;
        return mvccdElement ;
    }
}
