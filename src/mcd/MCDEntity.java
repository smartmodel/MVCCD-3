package mcd;

import constraints.Constraint;
import m.MRelationDegree;
import m.interfaces.IMClass;
import m.interfaces.IMCompletness;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.compliant.MCDCompliant;
import mcd.interfaces.IMCDCompliant;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import mcd.interfaces.IMCDSourceMLDRTable;
import mcd.services.MCDEntityService;
import project.ProjectElement;
import resultat.Resultat;
import stereotypes.Stereotype;

import java.util.ArrayList;

/**
 * Classe qui représente l'entité du modèle MCD.
 * Une entité a comme enfants (attribut childs hérité de MVCCDElement) (l'ordre peut différer):
 * (1) un conteneur d'attributs (MCDContAttributes);
 * (2) un conteneur de contraintes (MCDContConstrains);
 * (3) un conteneur d'extrémité de relation (MCDContRelEnds), qui contient autant des extrémités
 * d'associations (MCDAssEnd), que des extrémités de lien d'entité associative (MCDLinkEnd) ou que des extrémités
 * de liens de généralisation/spécialisation (MCDGSEnd).
 */
public class MCDEntity extends MCDElement implements IMClass, IMCompletness, IMCDElementWithTargets, IMCDSourceMLDRTable,
        IMCDCompliant {

    private static final long serialVersionUID = 1000;


    //private String shortName ;
    private boolean entAbstract = false;
    private boolean ordered = false;
    private boolean journal = false;
    private boolean audit = false;

    private String mldrTableName = "";

    public MCDEntity(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MCDEntity(ProjectElement parent, String name) {
        super(parent, name);
        setMldrTableName(name);
    }

    public MCDEntity(ProjectElement parent) {
        super (parent);
    }

    public void setName(String name){
        super.setName(name);
        setMldrTableName(name);
    }

    public boolean isEntAbstract() {
        return entAbstract;
    }

    public void setEntAbstract(boolean entAbstract) {
        this.entAbstract = entAbstract;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public boolean isJournal() {
        return journal;
    }

    public void setJournal(boolean journal) {
        this.journal = journal;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public String getMldrTableName() {
        return mldrTableName;
    }

    public void setMldrTableName(String mldrTableName) {
        this.mldrTableName = mldrTableName;
    }

    public ArrayList<MCDAttribute> getMCDAttributes() {
        for (MVCCDElement mvccdElement : getChilds()){
           if (mvccdElement instanceof MCDContAttributes) {
               MCDContAttributes mcdContAttributes = (MCDContAttributes) mvccdElement;
               return mcdContAttributes.getMCDAttributes();
           }
        }
        return new ArrayList<MCDAttribute>();
    }

    public MCDAttribute getMCDAttributeById(int id){
        return (MCDAttribute) this.getMCDContAttributes().getChildById(id);
    }

    public MCDContAttributes getMCDContAttributes() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContAttributes) {
                return (MCDContAttributes) mvccdElement;
            }
        }
        return null;
    }


    public ArrayList<MCDConstraint> getMCDConstraints() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContConstraints) {
                MCDContConstraints mcdContConstraints = (MCDContConstraints) mvccdElement;
                return mcdContConstraints.getMCDConstraints();
            }
        }
        return new ArrayList<MCDConstraint>();
    }


    public ArrayList<MCDUnicity> getMCDUnicities() {
        ArrayList<MCDUnicity> resultat = new ArrayList<MCDUnicity>();
        for (MCDConstraint mcdConstraint: getMCDConstraints() ){
            if (mcdConstraint instanceof MCDUnicity) {
                resultat.add((MCDUnicity) mcdConstraint);
            }
        }
        return resultat;
    }

    public ArrayList<MCDNID> getMCDNIDs() {
        ArrayList<MCDNID> resultat = new ArrayList<MCDNID>();
        for (MCDConstraint mcdConstraint: getMCDConstraints() ){
            if (mcdConstraint instanceof MCDNID) {
                resultat.add((MCDNID) mcdConstraint);
            }
        }
        return resultat;
    }

    public ArrayList<MCDUnique> getMCDUniques() {
        ArrayList<MCDUnique> resultat = new ArrayList<MCDUnique>();
        for (MCDConstraint mcdConstraint: getMCDConstraints() ){
            if (mcdConstraint instanceof MCDUnique) {
                resultat.add((MCDUnique) mcdConstraint);
            }
        }
        return resultat;
    }


    public MCDContConstraints getMCDContConstraints() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContConstraints) {
                return (MCDContConstraints) mvccdElement;
            }
        }
        return null;
    }

    public MCDContRelEnds getMCDContRelEnds() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContRelEnds) {
                return (MCDContRelEnds) mvccdElement;
            }
        }
        return null;
    }


    public ArrayList<MCDLinkEnd> getMCDLinkEnds() {
        ArrayList<MCDLinkEnd> resultat = new ArrayList<MCDLinkEnd>();
        for (MVCCDElement mvccdElement : getMCDContRelEnds().getChilds()){
            if (mvccdElement instanceof MCDLinkEnd){
                resultat.add((MCDLinkEnd) mvccdElement);
            }
        }
        return resultat;
    }

    public static String getClassShortNameUI() {
        return "Entité";
    }

    public Resultat treatCompliant(){
        MCDCompliant mcdCompliant = new MCDCompliant();
        return mcdCompliant.check(this);
    }

    public boolean isDuplicateMCDAttributeAID(){
        int nbAid = 0;
        for (MCDAttribute mcdAttribute : getMCDAttributes()){
            if (mcdAttribute.isAid()){
                nbAid++;
            }
        }
        return nbAid > 1;
    }

    public MCDAttribute getMCDAttributeAID(){
        for (MCDAttribute mcdAttribute : getMCDAttributes()){
            if (mcdAttribute.isAid()){
                return mcdAttribute;
            }
        }
        return null;
    }

    public boolean contentIdentifier(){
        return ( getMCDAttributeAID() != null) || (getMCDNIDs().size() > 0) ;
    }


    public ArrayList<MCDRelation> getMCDRelations(){
        return MCDEntityService.getMCDRelations(this);
    }

    public ArrayList<MCDGSEnd> getGSEndsGeneralize(){
        return MCDEntityService.getGSEndsGeneralize(this);
    }

    // Un tableau est retourné car lors de la saisie plusieurs liens peuvent être établis!
    // C'est lors du contrôle de conformité que je vérifie qu'il n'y a qu'un lien vers l'entité généralisée
    public ArrayList<MCDGSEnd> getGSEndSpecialize(){
        return MCDEntityService.getGSEndsSpecialize(this);
    }

    // Un tableau est retourné car lors de la saisie plusieurs liens peuvent être établis!
    // C'est lors du contrôle de conformité que je vérifie qu'il n'y a qu'un lien d'entité associative
    public ArrayList<MCDLinkEnd> getLinkEnds(){
        return MCDEntityService.getMCDLinkEnds(this);
    }

    public boolean isGeneralized(){
        return getGSEndsGeneralize().size() > 0;
    }

    public boolean isSpecialized(){
        return getGSEndSpecialize().size() == 1;
    }

    public boolean isLinkedEA(){
        return getLinkEnds().size() == 1;
    }

    public MRelationDegree getLinkedEADegree(){
        if (getLinkEnds().size() == 1){
            //return ((MCDAssociation) getLinkEnds().get(0).getMcdLink().getEndAssociation().getMcdRelation()).getDegree();
            return ((MCDAssociation) getLinkEnds().get(0).getMcdLink().getEndAssociation().getmElement()).getDegree();
        }
        return null;
    }

    public boolean isLinkedEANN(){
        return getLinkedEADegree() == MRelationDegree.DEGREE_MANY_MANY;
    }

    public boolean isLinkedEAPseudo(){
        return getLinkedEADegree() != MRelationDegree.DEGREE_MANY_MANY;
    }

    public boolean isInd(){
        return (getMCDAssEndsIdCompChild().size() == 0) && (! isLinkedEA()) && (! isSpecialized())
                && contentIdentifier();
    }

    public boolean isChildOfIdNat(){
        return (getMCDAssEndsIdNatChild().size() > 0) ;
    }
    public boolean isNoInd(){
        return ! isInd();
    }

    public boolean isPotentialInd(){
        return (getMCDAssEndsIdCompChild().size() == 0) && (! isLinkedEA()) && (! isSpecialized())
                && (!contentIdentifier());
    }

    public boolean isDep(){
        return (getMCDAssEndsIdCompChild().size() == 1) && (! isLinkedEA()) && (! isSpecialized())
                && contentIdentifier();
    }

    public boolean isPotentialDep(){
        return (getMCDAssEndsIdCompChild().size() == 1) && (! isLinkedEA()) && (! isSpecialized())
                && (!contentIdentifier());
    }

    public boolean isNAire(){
        return (getMCDAssEndsIdCompChild().size() > 1) && (! isLinkedEA()) && (! isSpecialized())
                && (!contentIdentifier());
    }

    public boolean isNAireDep(){
        return (getMCDAssEndsIdCompChild().size() > 1) && (! isLinkedEA()) && (! isSpecialized())
                && contentIdentifier();
    }

    public boolean isEntAssOrDescendant(){
        return isEntAss() || isEntAssDep();
    }

    public boolean isNAireOrDescendant(){
        return isNAire() || isNAireDep();
    }

    public boolean isEntConcret(){
        return isInd() || isDep() || isEntAssOrDescendant() || isNAireOrDescendant() || isSpecialized();
    }

    public boolean isPotentialSpecAttrAID(){
        return isSpecialized() && (getMCDAttributeAID() != null);
    }

    public boolean isPotentialSpecAssIdComp(){
        return isSpecialized() && (getMCDAssEndsIdCompChild().size() > 0);
    }

    public boolean isPseudoEntAss(){
        return isLinkedEA() && isLinkedEAPseudo() && (!contentIdentifier());
    }

    public boolean isPotentialPseudoEntAss(){
        return isLinkedEA() && isLinkedEAPseudo() && contentIdentifier();
    }

    public boolean isEntAss(){
        return isLinkedEA() && isLinkedEANN() && (!contentIdentifier());
    }


    public boolean isEntAssDep(){
        return isLinkedEA() && isLinkedEANN() && contentIdentifier();
    }

    public MCDEntityNature getNature(){
        return MCDEntityService.getNature(this);
    }

    public ArrayList<MCDEntity> getSisters(){
        ArrayList<MCDEntity> resultat = new ArrayList<MCDEntity>();
        for (MVCCDElement mvccdElement : super.getBrothers()){
            if (mvccdElement instanceof MCDEntity){
                resultat.add((MCDEntity) mvccdElement) ;
            }
        }
        return resultat;
    }

    // Nouveaux getMCDAssEnd...

    public ArrayList<MCDAssEnd> getMCDAssEnds() {
        return MCDEntityService.getMCDAssEnds(this);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsNN() {
        return MCDEntityService.getMCDAssEndsNN(this);
    }

    public ArrayList<MCDAssEnd> getMCDAssEnds1NParent() {
        return MCDEntityService.getMCDAssEnds1N(this, true);
    }

    public ArrayList<MCDAssEnd> getMCDAssEnds1NChild() {
        return MCDEntityService.getMCDAssEnds1N(this, false);
    }

    public ArrayList<MCDAssEnd> getMCDAssEnds11Parent() {
        return MCDEntityService.getMCDAssEnds11(this, true);
    }

    public ArrayList<MCDAssEnd> getMCDAssEnds11Child() {
        return MCDEntityService.getMCDAssEnds11(this, false);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsNotNNParent() {
        return MCDEntityService.getMCDAssEndsNotNN(this, true);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsNotNNChild() {
        return MCDEntityService.getMCDAssEndsNotNN(this, false);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsIdCompParent() {
        return MCDEntityService.getMCDAssEndsAssNature(this, true, MCDAssociationNature.IDCOMP);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsIdCompChild() {
        return MCDEntityService.getMCDAssEndsAssNature(this, false, MCDAssociationNature.IDCOMP);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsIdNatParent() {
        return MCDEntityService.getMCDAssEndsAssNature(this, true, MCDAssociationNature.IDNATURAL);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsIdNatChild() {
        return MCDEntityService.getMCDAssEndsAssNature(this, false, MCDAssociationNature.IDNATURAL);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsSimCPParent() {
        return MCDEntityService.getMCDAssEndsAssNature(this, true, MCDAssociationNature.CP);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsSimCPChild() {
        return MCDEntityService.getMCDAssEndsAssNature(this, false, MCDAssociationNature.CP);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsNoIdParent() {
        return MCDEntityService.getMCDAssEndsAssNature(this, true, MCDAssociationNature.NOID);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsNoIdChild() {
        return MCDEntityService.getMCDAssEndsAssNature(this, false, MCDAssociationNature.NOID);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsNoIdOptionnalParent() {
        return MCDEntityService.getMCDAssEndsNoIdOptionnal(this, true);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsNoIdOptionnalChild() {
        return MCDEntityService.getMCDAssEndsNoIdOptionnal(this, false);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsIdParent() {
        return MCDEntityService.getMCDAssEndsId(this, true);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsIdChild() {
        return MCDEntityService.getMCDAssEndsId(this, false);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsLinkNN(){
        return MCDEntityService.getMCDAssEndsLinkNN(this);
    }

    public ArrayList<MCDAssEnd> getMCDAssEndsStructureIdForParameters(){
        return MCDEntityService.getMCDAssEndsStructureIdForParameters(this);
    }


    public void delete(){
        // Suppression des relations attachées à l'entité.
        //TODO-0 A modifier en GetMRelations() pour intégrer OCLConstraint et OCLDependencies
        for (MCDRelation mcdRelation : this.getMCDRelations()){
            mcdRelation.delete();
        }
        super.delete();
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        return null;
    }

    @Override
    public String getStereotypesInBox() {
        return null;
    }

    @Override
    public String getStereotypesInLine() {
        return null;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        return null;
    }

    @Override
    public String getConstraintsInBox() {
        return null;
    }

    @Override
    public String getConstraintsInLine() {
        return null;
    }
}
