package mdr;

import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithSource;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRColumn;
import project.ProjectElement;

public abstract class MDRColumn extends MDRElement implements IMDRElementWithSource, IMDRParameter, IMDRElementNamingPreferences {

    private String datatypeLienProg = null;
    private String datatypeConstraintLienProg = null;
    private Integer size = null;
    private Integer scale = null;

    private boolean mandatory = false ;
    private boolean frozen = false;
    private boolean uppercase = false;

    private String initValue = null;
    private String derivedValue = null;

    //private boolean pk = false;
    //private boolean fk = false;

    public static final String CLASSSHORTNAMEUI = "Colonne";

    private  static final long serialVersionUID = 1000;

    private MLDRColumn mldrColumnPK = null;



    public MDRColumn(ProjectElement parent) {
        super(parent);
    }

    public MDRColumn(ProjectElement parent, MLDRColumn mldrColumnPK) {
        super(parent);
        this.mldrColumnPK = mldrColumnPK;
    }





    public String getDatatypeLienProg() {
        return datatypeLienProg;
    }

    public void setDatatypeLienProg(String datatypeLienProg) {
        this.datatypeLienProg = datatypeLienProg;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getDatatypeConstraintLienProg() {
        return datatypeConstraintLienProg;
    }

    public void setDatatypeConstraintLienProg(String datatypeConstraintLienProg) {
        this.datatypeConstraintLienProg = datatypeConstraintLienProg;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public String getDerivedValue() {
        return derivedValue;
    }

    public void setDerivedValue(String derivedValue) {
        this.derivedValue = derivedValue;
    }

    public boolean isPk() {
        return getMDRTableAccueil().getMDRColumnsPK().contains(this);
    }


    public boolean isFk() {
        return getMDRTableAccueil().getMDRColumnsFK().contains(this);
    }

    public MDRFK getFk() {
        for (MDRFK mdrFK : getMDRTableAccueil().getMDRFKs()) {
            if (mdrFK.getMDRColumns().contains(this)){
                return mdrFK;
            }
        }
        return null;
    }



    public MLDRColumn getMLDRColumnPK() {
        return mldrColumnPK;
    }

    @Override
    public String getClassShortNameUI() {
        return null;
    }

    public MDRTable getMDRTableAccueil(){
        return (MDRTable) getParent().getParent();
    }
}
