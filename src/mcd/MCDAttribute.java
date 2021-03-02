package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import m.interfaces.IMCompletness;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDAttributeService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDAttribute extends MCDElement implements IMCompletness, IMCDParameter, IMCDElementWithTargets {

    private static final long serialVersionUID = 1000;

    public static final String CLASSSHORTNAMEUI = "Attribute";

    private boolean aid = false;
    private boolean aidDep = false;
    private boolean mandatory = false ;
    private boolean list = false;

    private boolean frozen = false;
    private boolean ordered = false;

    private boolean uppercase = false;

    private String datatypeLienProg = null;
    private Integer size = null;
    private Integer scale = null;


    private String initValue = null;
    private String derivedValue = null;

    private String domain = null;



    public MCDAttribute(ProjectElement parent) {
        super(parent);
    }


    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
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

    public boolean isDerived(){
        return StringUtils.isNotEmpty(getDerivedValue());
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isAid() {
        return aid;
    }

    public void setAid(boolean aid) {
        this.aid = aid;
    }

    public boolean isAidDep() {
        return aidDep;
    }

    public void setAidDep(boolean aidDep) {
        this.aidDep = aidDep;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public ArrayList<Stereotype> getToStereotypes(){
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (aid){
            resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                    preferences.STEREOTYPE_AID_LIENPROG));
        }
        if (mandatory){
            resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                    preferences.STEREOTYPE_M_LIENPROG));
        }
        if (list){
            resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                    preferences.STEREOTYPE_L_LIENPROG));
        }

        if (partOfNIds().size()> 0){
            for (MCDNID nid : partOfNIds()){
                resultat.add(nid.getDefaultStereotype());
            }
        }

        return resultat;
    }

    public ArrayList<Constraint> getToConstraints(){
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (ordered){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_ORDERED_LIENPROG));
        }

        if (frozen){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_FROZEN_LIENPROG));
        }

        return resultat;
    }



    @Override
    public  String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }

    public MCDEntity getEntityAccueil(){
        return (MCDEntity) getParent().getParent();
    }
    public ArrayList<MCDNID> partOfNIds(){
        return MCDAttributeService.partOfNIds(this);
    }
}
