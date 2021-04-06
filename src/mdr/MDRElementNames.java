package mdr;

import exceptions.TransformMCDException;

import java.io.Serializable;

// Cette classe n'est pas un héritier de MVCCDElement !
public class MDRElementNames implements Serializable {

    private  static final long serialVersionUID = 1000;

    private String name30 = "";
    private String name60 = "";
    private String name120 = "";

    public MDRElementNames(){

    }

    /*
    public MDRElementNames(String name30, String name60, String name120) {
        this.name30 = name30;
        this.name60 = name60;
        this.name120 = name120;
    }

     */

    public String getName30() {
        return name30;
    }

    public void setName30(String name30) {
        this.name30 = name30;
    }

    public String getName60() {
        return name60;
    }

    public void setName60(String name60) {
        this.name60 = name60;
    }

    public String getName120() {
        return name120;
    }

    public void setName120(String name120) {
        this.name120 = name120;
    }

    public String getNameByNameLength(MDRNamingLength nameLength) {
        if (nameLength == MDRNamingLength.LENGTH30){
            return getName30();
        }
        else if (nameLength == MDRNamingLength.LENGTH60){
            return getName60();
        }
        else if (nameLength == MDRNamingLength.LENGTH120){
            return getName120();
        }
        else {
            throw new TransformMCDException("MDRElementNames :  Paramètre nameLength inconnu !" );
        }
    }

    public void setElementName(String name, MDRNamingLength nameLength) {
        if (nameLength == MDRNamingLength.LENGTH30){
            setName30(name);
        }
        else if (nameLength == MDRNamingLength.LENGTH60){
            setName60(name);
        }
        else if (nameLength == MDRNamingLength.LENGTH120){
            setName120(name);
        }
        else {
            throw new TransformMCDException("MDRElementNames :  Paramètre nameLength inconnu !" );
        }
    }
}
