package datatypes;

import exceptions.TransformMCDException;
import main.MVCCDElement;
import messages.MessagesBuilder;

public abstract class MDDatatype extends MVCCDElement {

    public static final int SIZEMANDATORY = 10;
    public static final int SIZEDEFAULT = 11;
    public static final int SIZEMIN = 12;
    public static final int SIZEMAX = 13;

    public static final int SCALEMANDATORY = 20;
    public static final int SCALEDEFAULT = 21;
    public static final int SCALEMIN = 22;
    public static final int SCALEMAX = 23;

    private boolean abstrait = false;

    private Boolean sizeMandatory = null;
    private Integer sizeDefault=null;
    private Integer sizeMin = null;
    private Integer sizeMax = null;
    private Boolean scaleMandatory = null;
    private Integer scaleDefault= null;
    private Integer scaleMax = null;
    private Integer scaleMin = null;

    private String lienProg= null;

    public MDDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name);
        init(name, abstrait);
    }

    public MDDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name);
        init(lienProg, abstrait);
    }

    private void init(String lienProg, boolean abstrait) {
        this.lienProg = lienProg;
        this.abstrait = abstrait;
    }

    public boolean isAbstrait() {
        return abstrait;
    }

    public void setAbstrait(boolean abstrait) {
        this.abstrait = abstrait;
    }

    public Boolean getSizeMandatory() {
        return sizeMandatory;
    }

    public Boolean isSizeMandatoryWithInherit(){
        return (Boolean) getPropertyWithInherit(SIZEMANDATORY);
    }

    public MDDatatype getSizeMandatoryFrom(){
        return getPropertyNotNullFrom(SIZEMANDATORY);
    }


    public void setSizeMandatory(Boolean sizeMandatory) {
        this.sizeMandatory = sizeMandatory;
    }

    public Integer getSizeMax() {
        return sizeMax;
    }


    public Integer getSizeMaxWithInherit(){
        return (Integer) getPropertyWithInherit(SIZEMAX);
    }

    public MDDatatype getSizeMaxFrom(){
        return getPropertyNotNullFrom(SIZEMAX);
    }

    public void setSizeMax(Integer sizeMax) {
        this.sizeMax = sizeMax;
    }

    public Boolean getScaleMandatory() {
        return scaleMandatory;
    }

    public Boolean isScaleMandatoryWithInherit(){
        return (Boolean) getPropertyWithInherit(SCALEMANDATORY);
    }

    public MDDatatype getScaleMandatoryFrom(){
        return getPropertyNotNullFrom(SCALEMANDATORY);
    }

    public void setScaleMandatory(Boolean scaleMandatory) {
        this.scaleMandatory = scaleMandatory;
    }

    public String getLienProg() {
        return lienProg;
    }

    public void setLienProg(String lienProg) {
        this.lienProg = lienProg;
    }

    public boolean isSizeMandatory() {
        return sizeMandatory;
    }

    public void setSizeMandatory(boolean sizeMandatory) {
        this.sizeMandatory = sizeMandatory;
    }

    public boolean isScaleMandatory() {
        return scaleMandatory;
    }

    public void setScaleMandatory(boolean scaleMandatory) {
        this.scaleMandatory = scaleMandatory;
    }

    public Integer getSizeDefault() {
        return sizeDefault;
    }


    public Integer getSizeDefaultWithInherit(){
        return (Integer) getPropertyWithInherit(SIZEDEFAULT);
    }

    public MDDatatype getSizeDefaultFrom(){
        return getPropertyNotNullFrom(SIZEDEFAULT);
    }


    public void setSizeDefault(Integer sizeDefault) {
        this.sizeDefault = sizeDefault;
    }

    public Integer getScaleDefault() {
        return scaleDefault;
    }

    public Integer getScaleDefaultWithInherit(){
        return (Integer) getPropertyWithInherit(SCALEDEFAULT);
    }

    public MDDatatype getScaleDefaultFrom(){
        return getPropertyNotNullFrom(SCALEDEFAULT);
    }


    public void setScaleDefault(Integer scaleDefault) {
        this.scaleDefault = scaleDefault;
    }



    public Integer getScaleMax() {
        return scaleMax;
    }


    public Integer getScaleMaxWithInherit(){
        return (Integer) getPropertyWithInherit(SCALEMAX);
    }

    public MDDatatype getScaleMaxFrom(){
        return getPropertyNotNullFrom(SCALEMAX);
    }

    public void setScaleMax(Integer scaleMax) {
        this.scaleMax = scaleMax;
    }

    public Integer getSizeMin() {
        return sizeMin;
    }

    public Integer getSizeMinWithInherit(){
        return (Integer) getPropertyWithInherit(SIZEMIN);
    }

    public MDDatatype getSizeMinFrom(){
        return getPropertyNotNullFrom(SIZEMIN);
    }
    public void setSizeMin(Integer sizeMin) {
        this.sizeMin = sizeMin;
    }

    public Integer getScaleMin() {
        return scaleMin;
    }


    public Integer getScaleMinWithInherit(){
        return (Integer) getPropertyWithInherit(SCALEMIN);
    }

    public MDDatatype getScaleMinFrom(){
        return getPropertyNotNullFrom(SCALEMIN);
    }

    public void setScaleMin(Integer scaleMin) {
        this.scaleMin = scaleMin;
    }



    public Object getPropertyWithInherit(int property) throws TransformMCDException {
        if (property == SIZEMANDATORY) {
            if (sizeMandatory != null) {
                return sizeMandatory;
            }
        } else if (property == SIZEDEFAULT) {
            if (sizeDefault != null) {
                return sizeDefault;
            }
        } else if (property == SIZEMIN) {
            if (sizeMin != null) {
                return sizeMin;
            }
        } else if (property == SIZEMAX) {
            if (sizeMax != null) {
                return sizeMax;
            }
        } else if (property == SCALEMANDATORY) {
            if (scaleMandatory != null) {
                return scaleMandatory;
            }
        } else if (property == SCALEDEFAULT) {
            if (scaleDefault != null) {
                return scaleDefault;
             }
        } else if (property == SCALEMIN) {
            if (scaleMin != null) {
                return scaleMin;
            }
        } else if (property == SCALEMAX) {
            if (scaleMax != null) {
                return scaleMax; 
            }
        } else {
            String message = MessagesBuilder.getMessagesProperty("error.mddatatypes.property",
                    String.valueOf(property));
            throw new TransformMCDException(this.getClass().getName() + " - " + message);
        }
        if (getParent() != null){
            if (getParent() instanceof MDDatatype){
                return ((MDDatatype) getParent()).getPropertyWithInherit(property);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public MDDatatype getPropertyNotNullFrom(int property) throws TransformMCDException {
        if (property == SIZEMANDATORY) {
            if (sizeMandatory != null) {
                return this;
            }
        } else if (property == SIZEDEFAULT) {
            if (sizeDefault != null) {
                return this;
            }
        } else if (property == SIZEMIN) {
            if (sizeMin != null) {
                return this;
            }
        } else if (property == SIZEMAX) {
            if (sizeMax != null) {
                return this;
            }
        } else if (property == SCALEMANDATORY) {
            if (scaleMandatory != null) {
                return this;
            }
        } else if (property == SCALEDEFAULT) {
            if (scaleDefault != null) {
                return this;
            }
        } else if (property == SCALEMIN) {
            if (scaleMin != null) {
                return this;
            }
        } else if (property == SCALEMAX) {
            if (scaleMax != null) {
                return this;
            }
        } else {
            String message = MessagesBuilder.getMessagesProperty("error.mddatatypes.property",
                    String.valueOf(property));
            throw new TransformMCDException(this.getClass().getName() + " - " + message);
        }
        if (getParent() != null){
            if (getParent() instanceof MDDatatype){
                return ((MDDatatype) getParent()).getPropertyNotNullFrom(property);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean isDescendantOf(MDDatatype ancestor){
        boolean resultat = false;
        if (getParent() != null){
            if (getParent() instanceof MDDatatype) {
                MDDatatype parent = (MDDatatype) getParent();
                if (parent == ancestor) {
                    resultat = true;
                } else {
                    resultat = parent.isDescendantOf(ancestor);
                }
            }
        }
        return resultat;
    }

    public boolean isSelfOrDescendantOf(MDDatatype ancestor){
        if (this == ancestor){
            return true;
        } else {
            return isDescendantOf(ancestor);
        }
    }
}
