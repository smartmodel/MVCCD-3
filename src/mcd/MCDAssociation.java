package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import m.IMCompletness;
import m.MRelationDegree;
import m.services.MRelationService;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDAssociationService;
import mcd.services.MCDRelationService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDAssociation extends MCDRelation implements IMCompletness, IMCDParameter {

    private  static final long serialVersionUID = 1000;

    public static final String CLASSSHORTNAMEUI = "Association";


    private MCDAssociationNature nature = MCDAssociationNature.NOID;
    private boolean frozen = false;
    private boolean deleteCascade = false;
    private Boolean oriented = null;

    public MCDAssociation(MCDElement parent) {

        super(parent);
    }


    public MCDAssociation(MCDElement parent, String name) {

        super(parent, name);
    }

    public MCDAssEnd getFrom() {

        return (MCDAssEnd) super.getA();
    }

    public void setFrom(MCDAssEnd from) {
        super.setA(from);
        from.setDrawingDirection(MCDAssEnd.FROM);
    }

    public MCDAssEnd getTo() {
        return (MCDAssEnd) super.getB();
    }

    public void setTo(MCDAssEnd to) {
        super.setB(to);
        to.setDrawingDirection(MCDAssEnd.TO);
    }

    public String getNameId(){
        return MCDAssociationService.buildNamingId(getFrom().getMcdEntity(), getTo().getMcdEntity(), this.getName());
    }

   public String getShortNameId() {
       return MCDAssociationService.buildNamingId(getFrom().getMcdEntity(), getTo().getMcdEntity(), this.getShortName());
    }

   public String getLongNameId() {
       return MCDAssociationService.buildNamingId(getFrom().getMcdEntity(), getTo().getMcdEntity(), this.getLongName());
    }

    @Override
    public String getNameTree(){
        String namingAssociation = computeNamingAssociation();
        return MCDRelationService.getNameTree(this, namingAssociation, false, null);
    }

    public String getNamePath(int pathMode){
        String namingAssociation = computeNamingAssociation();
        return MCDRelationService.getNameTree(this, namingAssociation, true, pathMode);
    }

    private String computeNamingAssociation(){
        String namingAssociation ;
        if (StringUtils.isNotEmpty(getFrom().getName())  && StringUtils.isNotEmpty(getTo().getName())){
            namingAssociation = Preferences.MCD_NAMING_ASSOCIATION_ARROW_RIGHT +
                    getFrom().getName() +
                    Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR  +
                    getTo().getName() +
                    Preferences.MCD_NAMING_ASSOCIATION_ARROW_LEFT;
        } else {
            namingAssociation = Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR +
                    this.getName() + Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;
        }
        return namingAssociation;
    }

    public MCDAssEnd getMCDAssEndOpposite(MCDAssEnd mcdAssEnd) {
        if (this.getFrom() == mcdAssEnd){
            return this.getTo();
        }
        if (this.getTo() == mcdAssEnd){
            return this.getFrom();
        }

        throw new CodeApplException("L'extrémité d'association passée en paramètre n'existe pas pour cette association ");

    }

    public MCDAssociationNature getNature() {
        return nature;
    }

    public void setNature(MCDAssociationNature nature) {
        this.nature = nature;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isDeleteCascade() {
        return deleteCascade;
    }

    public void setDeleteCascade(boolean deleteCascade) {
        this.deleteCascade = deleteCascade;
    }

    public Boolean getOriented() {
        return oriented;
    }

    public void setOriented(Boolean oriented) {
        this.oriented = oriented;
    }

    public boolean isNoId(){
        if (nature != null){
            return nature == MCDAssociationNature.NOID;
        }
        return false;
    }


    public boolean isIdNatural(){
        if (nature != null){
            return nature == MCDAssociationNature.IDNATURAL;
        }
        return false;
    }

    public boolean isIdComp(){
        if (nature != null){
            return nature == MCDAssociationNature.IDCOMP;
        }
        return false;
    }

    public boolean isCP(){
        if (nature != null){
            return nature == MCDAssociationNature.CP;
        }
        return false;
    }

    public boolean isDegreeNN(){
        return getDegree() == MRelationDegree.DEGREE_MANY_MANY;
    }

    public MRelationDegree getDegree(){
        return MRelationService.computeDegree(getFrom().getMultiMaxStd(), getTo().getMultiMaxStd());
    }


    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }

    @Override
    public ArrayList<Stereotype> getToStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (PreferencesManager.instance().preferences().getGENERAL_RELATION_NOTATION().equals(
                Preferences.GENERAL_RELATION_NOTATION_STEREOTYPES)){
            if (isIdComp()){
                resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                        preferences.STEREOTYPE_CID_LIENPROG));
            }
            if (isIdNatural()){
                resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                        preferences.STEREOTYPE_NID_LIENPROG));
            }
        }

        if (isCP()){
            resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                    preferences.STEREOTYPE_CP_LIENPROG));
        }
        return resultat;
    }

    @Override
    public ArrayList<Constraint> getToConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (frozen){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_FROZEN_LIENPROG));
        }
        if (deleteCascade){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_DELETECASCADE_LIENPROG));
        }
        if (oriented != null){
            if (oriented){
                resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                        preferences.CONSTRAINT_ORIENTED_LIENPROG));
            } else {
                resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                        preferences.CONSTRAINT_NONORIENTED_LIENPROG));
            }
        }

        return resultat;
    }

    public boolean isReflexive(){
        return getFrom().getMcdEntity() == getTo().getMcdEntity();
    }
}
