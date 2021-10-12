package mcd;

import console.ViewLogsManager;
import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mcd.services.MCDRelationService;
import messages.MessagesBuilder;
import mldr.MLDRModel;
import mldr.MLDRTable;
import preferences.Preferences;
import preferences.PreferencesManager;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;
import utilities.window.DialogMessage;

import java.util.ArrayList;

public class MCDLink extends MCDRelation {

    private  static final long serialVersionUID = 1000;
    public static final String CLASSSHORTNAMEUI = "Lien associatif";


    public MCDLink(MCDElement parent) {
        super(parent);
    }

    public MCDLink(MCDElement parent, int id) {
        super(parent, id);
    }

    public MCDLink(MCDElement parent, String name) {
        super(parent, name);
    }

    /**
     * Retourne l'extrémité du lien d'entité associative (c'est-à-dire MCDLinkEnd) se trouvant du côté de l'entité associative.
     */
    public MCDLinkEnd getEndEntity() {
        return  (MCDLinkEnd)  super.getA();
    }

    public MCDEntity getEntity() {
        return  (MCDEntity)  getEndEntity().getmElement();
    }

    public void setEndEntity(MCDRelEnd endEntity) {
        super.setA(endEntity);
        endEntity.setDrawingDirection(MCDRelEnd.ELEMENT);
    }

    /**
     * Retourne l'extrémité du lien d'entité associative (c'est-à-dire MCDLinkEnd) se trouvant du côté de l'association.
     */
    public MCDLinkEnd getEndAssociation() {
        return (MCDLinkEnd) super.getB();
    }

    public MCDAssociation getAssociation() {
        return (MCDAssociation) getEndAssociation().getmElement();
    }

    public void setEndAssociation(MCDRelEnd endAssociation) {
        super.setB(endAssociation);
        endAssociation.setDrawingDirection(MCDRelEnd.RELATION);
    }

    @Override
    public String getNameTree(){
        MCDLinkEnd assEndEntity = this.getEndEntity();
        MCDEntity entity = this.getEntity();
        MCDAssociation association = this.getAssociation();
        return assEndEntity.getPath() + Preferences.MCD_NAMING_LINK + association.getNameTreePath();
    }

    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }



    public void delete(){
        Resultat resultat = new Resultat();
        // Si nécessaire, changement de la source de la table : Association n:n --> Entité associative
        if (getAssociation().isDegreeNN()){
            IMCDModel mcdModelAccueil = this.getIMCDModelAccueil();
            for (MLDRModel mldrModel : IMCDModelService.getMLDRModels(mcdModelAccueil)){
                MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(getEntity());
                if (mldrTable != null){
                    mldrTable.setMcdElementSource(getAssociation());
                    String message = MessagesBuilder.getMessagesProperty("editor.link.change.source.to.association.nn",
                    new String[] {mldrTable.getNameTreePath(), getAssociation().getNameTreePath()} );
                    resultat.add(new ResultatElement(message, ResultatLevel.INFO));
                }
            }
        }
        if (resultat.getNbElementsAllLevels() > 0){
            ViewLogsManager.printResultat(resultat);
            //DialogMessage.showOk(null, message);
        }

        super.delete();
    }

}
