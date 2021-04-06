package mcd;

import exceptions.TransformMCDException;
import mcd.compliant.MCDCompliant;
import mcd.interfaces.IMCDCompliant;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContPackages;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mldr.MLDRModel;
import project.ProjectElement;
import resultat.Resultat;
import transform.mcdtomldr.MCDTransform;

public class MCDContModels extends MCDElement implements IMCDModel, /*IMCDNamePathParent, */IMCDContPackages,
        IMCDContContainer, IMCDCompliant {

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


    public Resultat treatTransform()  throws TransformMCDException {
        MCDTransform mcdTransform = new MCDTransform();
        // Il n'y a pas de modèles. Il faut donc tester toutes les entités
        return mcdTransform.transform(this);
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
