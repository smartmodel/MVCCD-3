package mdr;

import mdr.interfaces.IMDRElementWithIteration;
import mdr.interfaces.IMDRParameter;
import org.apache.commons.lang.StringUtils;
import project.ProjectElement;
import project.ProjectService;

public abstract class MDRParameter extends MDRElement implements IMDRElementWithIteration{

    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation

    //private IMDRParameter target = null;
    private Integer targetId ;

    // Pour le niveau logique
    public MDRParameter(ProjectElement parent, IMDRParameter target) {
        super(parent);
        this.targetId = target.getIdProjectElement();
    }

    // Utilisé pour le parameter de niveau MPDR lors de la transformation MLDR=>MPDR. En effet dans ce cas le Parameter de niveau physique est d'abord créé "vide" (sans target) et le target est setté par la suite.
    public MDRParameter(ProjectElement parent) {
        super(parent);
    }

    @Override
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    // Pour l'affectation de niveau physique
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public IMDRParameter getTarget() {
        if (targetId != null) {
            return (IMDRParameter) ProjectService.getElementById(targetId);
        }
        return null;
    }


    public String getName(){
        String name = super.getName();
        if (getTarget() != null){
            if (StringUtils.isEmpty(name)){
                name = getTarget().getName();
            }
        }
        return name;
    }


    public MDRConstraint getMDRConstraintAccueil(){
        return (MDRConstraint) getParent();
    }


    public MDRTable getMDRTableAccueil(){
        return (MDRTable) getMDRConstraintAccueil().getMDRTableAccueil();
    }

}
