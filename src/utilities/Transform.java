package utilities;

import main.MVCCDElement;

public class Transform {

    public static void name(MVCCDElement mvccdElement, String newName){
        if (mvccdElement.getName() != null) {
            if (!mvccdElement.getName().equals(newName)) {
                mvccdElement.setName(newName);
            }
        } else {
            mvccdElement.setName(newName);
        }
    }
}
