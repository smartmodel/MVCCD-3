package mcd;

import main.MVCCDElement;
import mcd.compliant.MCDCompliant;
import mcd.interfaces.*;
import mcd.services.IMCDModelService;
import mldr.MLDRModel;
import project.ProjectElement;
import resultat.Resultat;
import transform.mcdtomldr.MCDTransform;

/**
 * L'instance correspond au modèle "MCD" existant dans le référentiel. Ce modèle MCD contient potentiellement lui-même
 * plusieurs modèles (MLDR).
 */
public class MCDContModels extends MCDElement implements IMCDModel, /*IMCDNamePathParent, */IMCDContPackages,
        IMCDContContainer, IMCDCompliant, IMPathOnlyRepositoryTree {

    private static final long serialVersionUID = 1000;
    private MLDRModel lastTransformedMLDRModel;

    public MCDContModels(ProjectElement parent, int id){
        super(parent, id);
    }

    public MCDContModels(ProjectElement parent, String name) {
        super(parent, name);
    }



    public MCDContModels(ProjectElement parent) {
        super (parent);
    }


    public Resultat treatCompliant(){
        MCDCompliant mcdCompliant = new MCDCompliant();
        // Il n'y a pas de modèles. Il faut donc tester toutes les entités du conteneur
        //ArrayList<String> resultat = mcdCompliant.check(ProjectService.getMCDEntities(), false);
        return mcdCompliant.check(IMCDModelService.getMCDEntities(this), false);
    }


    public Resultat treatTransform()   {
        MCDTransform mcdTransform = new MCDTransform();
        // Il n'y a pas de modèles. Il faut donc tester toutes les entités
        return mcdTransform.transform(this);
    }

    /**
     * Parcourt les enfants du modèle MCD, et retourne le premier qui est de type conteneur d'entités.
     * @return Le conteneur d'entités du modèle MCD. Si aucun conteneur d'entités n'est trouvé, null est retourné.
     */
    public MCDContEntities getEntities(){
        for(MVCCDElement childOfContEntities : this.getChilds()){
            if(childOfContEntities instanceof MCDContEntities){
                return (MCDContEntities) childOfContEntities;
            }
        }
        return null;
    }

    public MCDContRelations getRelations(){
        for(MVCCDElement childOfContEntities : this.getChilds()){
            if(childOfContEntities instanceof MCDContRelations){
                return (MCDContRelations) childOfContEntities;
            }
        }
        return null;
    }


    @Override
    public MLDRModel getLastTransformedMLDRModel() {
        return lastTransformedMLDRModel;
    }

    @Override
    public void setLastTransformedMLDRModel(MLDRModel lastTransformedMLDRModel) {
        this.lastTransformedMLDRModel = lastTransformedMLDRModel;
    }

}
