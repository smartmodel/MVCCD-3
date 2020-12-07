package project;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDContPackages;
import mcd.MCDEntityNature;
import mdr.MDRModel;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class ProjectService {


    public ProjectService() {

    }

    static Project getProjectRoot(ProjectElement projectElement) {
        if (projectElement instanceof Project) {
            return ((Project) projectElement);
        } else {
            return getProjectRoot((ProjectElement) projectElement.getParent());
        }
    }

    public static ProjectElement getElementById(int id) {
        Project project = MVCCDManager.instance().getProject();
        return getElementById(project, id);
    }

    public static ProjectElement getElementById(ProjectElement projectElement, int id) {
        ProjectElement resultat = null;
        for (MVCCDElement mvccdElement : projectElement.getChilds()) {
            if (mvccdElement instanceof ProjectElement) {
                ProjectElement child = (ProjectElement) mvccdElement;
                if (child.getId() == id) {
                    resultat = child;
                } else {
                    if (resultat == null) {
                        resultat = getElementById(child, id);
                    }
                }
            }
        }
        return resultat;
    }

    //TODO-1 Déplacer dans RepositoryService ?
    public static DefaultMutableTreeNode getNodeById(int id) {

        DefaultMutableTreeNode rootProject = MVCCDManager.instance().getRepository().getNodeProject();
        return getNodeById(rootProject, id);
    }

    public static DefaultMutableTreeNode getNodeById(DefaultMutableTreeNode root, int id) {
        DefaultMutableTreeNode resultat = null;
        if (root.getUserObject() instanceof ProjectElement) {
            ProjectElement projectElement = (ProjectElement) root.getUserObject();
            if (projectElement.getId() == id) {
                resultat = root;
            }
        }
        if (resultat == null){
            for (int i = 0; i < root.getChildCount(); i++) {
                if (resultat == null) {
                    resultat = getNodeById((DefaultMutableTreeNode) root.getChildAt(i), id);
                }
            }
        }
        return resultat;
    }


    public static ArrayList<ProjectElement> getProjectElements() {
        Project project = MVCCDManager.instance().getProject();
        return  getProjectElementsByParent(project);
    }

    public static ArrayList<ProjectElement> getProjectElementsByParent(ProjectElement parentProjectElement) {
        ArrayList<ProjectElement> resultat = new ArrayList<ProjectElement>();
        resultat.add(parentProjectElement);
        for (MVCCDElement mvccdElement : parentProjectElement.getChilds()){
            if (mvccdElement instanceof ProjectElement){
                ProjectElement childProjectElement  = (ProjectElement) mvccdElement;
                resultat.addAll(getProjectElementsByParent(childProjectElement));
            }
        }
        return resultat;
    }


    public static ArrayList<MCDElement> getMCDElements() {
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        for (ProjectElement projectElement : getProjectElements()){
            if (projectElement instanceof MCDElement){
                resultat.add((MCDElement)projectElement);
            }
        }
        return resultat;
    }


    public static ArrayList<MCDElement> getMCDElements(MCDElement root) {
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        for (ProjectElement projectElement : getProjectElementsByParent(root)){
            if (projectElement instanceof MCDElement){
                resultat.add((MCDElement)projectElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDPackage> getMCDPackages(){
        ArrayList<MCDPackage>  resultat = new ArrayList<MCDPackage>() ;
        for (MCDElement mcdElement : getMCDElements()){
            if (mcdElement instanceof MCDPackage){
                resultat.add((MCDPackage) mcdElement);
            }
        }
        return resultat;
    }

    public static ArrayList<IMCDContPackages> getIMCDContPackages(){
        ArrayList<IMCDContPackages>  resultat = new ArrayList<IMCDContPackages>() ;
        for (MCDElement mcdElement : getMCDElements()){
            if (mcdElement instanceof IMCDContPackages){
                resultat.add((IMCDContPackages) mcdElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDElement> getMCDElementsByNameTree(String nameTree){
        ArrayList<MCDElement>  resultat = new ArrayList<MCDElement>() ;
        for (MCDElement mcdElement : getMCDElements()){
            if (mcdElement.getNameTree().equals(nameTree)){
                resultat.add(mcdElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDContConstraints> getMCDContConstraints() {
        ArrayList<MCDContConstraints>  resultat = new ArrayList<MCDContConstraints>() ;
        for (MVCCDElement mvccdElement : getProjectElements()) {
            if (mvccdElement instanceof MCDContConstraints) {
                resultat.add( (MCDContConstraints) mvccdElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDContEntities> getMCDContEntities() {
        ArrayList<MCDContEntities>  resultat = new ArrayList<MCDContEntities>() ;
        for (MVCCDElement mvccdElement : getProjectElements()) {
            if (mvccdElement instanceof MCDContEntities) {
                resultat.add( (MCDContEntities) mvccdElement);
            }
        }
        return resultat;
    }

    public static MCDContModels getMCDContModels() {
        for (MVCCDElement mvccdElement : getProjectElements()) {
            if (mvccdElement instanceof MCDContModels) {
                return (MCDContModels) mvccdElement;
            }
        }
        return null;
    }

    public static ArrayList<MCDContRelations> getMCDContRelations() {
        ArrayList<MCDContRelations>  resultat = new ArrayList<MCDContRelations>() ;
        for (MVCCDElement mvccdElement : getProjectElements()) {
            if (mvccdElement instanceof MCDContRelations) {
                resultat.add( (MCDContRelations) mvccdElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDRelEnd> getMCDRelEnds(){
        ArrayList<MCDRelEnd>  resultat = new ArrayList<MCDRelEnd>() ;
        for (MCDElement aMCDElement : getMCDElements()){
            if (aMCDElement instanceof MCDRelEnd){
                resultat.add((MCDRelEnd) aMCDElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDEntity> getMCDEntities(){
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDElement aMCDElement : getMCDElements()){
            if (aMCDElement instanceof MCDEntity){
                resultat.add((MCDEntity) aMCDElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDEntity> getMCDEntities(MCDElement root){
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDElement aMCDElement : getMCDElements(root)){
            if (aMCDElement instanceof MCDEntity){
                resultat.add((MCDEntity) aMCDElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDEntity> getMCDEntitiesConcrets(MCDElement root){
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDEntity aMCDEntity : getMCDEntities(root)){
            if ( aMCDEntity.getNature() != MCDEntityNature.PSEUDOENTASS){
                resultat.add((MCDEntity) aMCDEntity);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDEntity> getMCDEntitiesIndependants(MCDElement root) {
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDEntity aMCDEntity : getMCDEntities(root)){
            if ( aMCDEntity.getNature() == MCDEntityNature.IND){
                resultat.add((MCDEntity) aMCDEntity);
            }
        }
        return resultat;
    }


    public static ArrayList<MCDEntity> getMCDEntitiesConcretsNoInd(MCDElement root) {
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDEntity aMCDEntity : getMCDEntities(root)){
            if (    (aMCDEntity.getNature() == MCDEntityNature.DEP) ||
                    (aMCDEntity.getNature() == MCDEntityNature.ENTASS) ||
                    (aMCDEntity.getNature() == MCDEntityNature.ENTASSDEP) ||
                    (aMCDEntity.getNature() == MCDEntityNature.NAIRE) ||
                    (aMCDEntity.getNature() == MCDEntityNature.NAIREDEP) ||
                    (aMCDEntity.getNature() == MCDEntityNature.SPEC) ){
                resultat.add((MCDEntity) aMCDEntity);
            }
        }
        return resultat;
    }


    // ByMCDElement : Element auquel est attaché la relation
    public static ArrayList<MCDRelEnd> getMCDRelEndsByMCDElement(MCDElement mcdElement){
        ArrayList<MCDRelEnd>  resultat = new ArrayList<MCDRelEnd>() ;
        for (MCDRelEnd aMCDRelEnd : getMCDRelEnds()){
            if (aMCDRelEnd.getMcdElement() == mcdElement ){
                resultat.add(aMCDRelEnd);
            }
        }
        return resultat;
    }



    public static ArrayList<MCDRelation> getMCDRelations() {
        ArrayList<MCDRelation>  resultat = new ArrayList<MCDRelation>() ;
        for (MCDElement aMCDElement : getMCDElements()){
            if (aMCDElement instanceof MCDRelation){
                resultat.add((MCDRelation) aMCDElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDRelation> getMCDRelationsChilds(MCDRelation mcdRelation){
        ArrayList<MCDRelation>  resultat = new ArrayList<MCDRelation>() ;
        for (MCDRelation aMCDRelation: getMCDRelations()){
            if ((aMCDRelation.getA() != null) && (aMCDRelation.getB() != null)) {
                if ((aMCDRelation.getA().getMcdElement() == mcdRelation) ||
                        (aMCDRelation.getB().getMcdElement() == mcdRelation)) {
                    resultat.add(aMCDRelation);
                }
            }
        }
        return resultat;
    }


    public static ArrayList<MDRModel> getMDRModels(){
        ArrayList<MDRModel>  resultat = new ArrayList<MDRModel>() ;
        for (ProjectElement projectElement : getProjectElements()){
            if (projectElement instanceof MDRModel){
                resultat.add((MDRModel) projectElement);
            }
        }
        return resultat;
    }


}

