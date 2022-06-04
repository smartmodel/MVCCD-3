package mpdr.tapis;

import mdr.MDRColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.tapis.interfaces.ITapisElementWithSource;
import project.ProjectElement;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MPDRColumnJnal extends MDRColumn implements IMPDRElement, ITapisElementWithSource {

    private static final long serialVersionUID = 1000;

    //protected MPDRDB mpdrDb;
    //ArrayList<Stereotype> stereotypes = new ArrayList<Stereotype>();
    IMPDRElement mpdrElementSource ;
    Stereotype sterereotypeJnal  ;

    // Constructeur pour les données techniques JN_xxx
    public MPDRColumnJnal(ProjectElement parent,
                          IMPDRElement mpdrElementSource,
                          Stereotype stereotypeJnal) {
        super(parent);
        this.mpdrElementSource = mpdrElementSource;
        this.sterereotypeJnal = stereotypeJnal;
    }

    // Constructeur pour les données de datas journalisées
    public MPDRColumnJnal(ProjectElement parent,
                          IMPDRElement mpdrElementSource
                          ) {
        super(parent);
        this.mpdrElementSource = mpdrElementSource;

    }

    public MPDRColumnJnal(ProjectElement parent, IMPDRElement mpdrElementSource, int id) {
        super(parent, id);
        this.mpdrElementSource = mpdrElementSource;
    }

    @Override
    public IMPDRElement getMpdrElementSource() {
        return mpdrElementSource;
    }

    @Override
    public void setMpdrElementSource(IMPDRElement mpdrElementSource) {
        this.mpdrElementSource = mpdrElementSource;
    }

    public Stereotype getSterereotypeJnal() {
        return sterereotypeJnal;
    }

    public ArrayList<Stereotype> getStereotypes(){
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();
        resultat.add(sterereotypeJnal);
        return resultat;
    }


    public MPDRTableJnal getMPDRTableJnalAccueil(){
        return (MPDRTableJnal) getParent().getParent();
    }

    public MPDRTable getMPDRTableToJournalize(){
        return (MPDRTable) getMPDRTableJnalAccueil().getParent().getParent();
    }

    public MPDRColumn getMpdrColumnSource(){
        if (getMpdrElementSource() instanceof MPDRColumn){
            return (MPDRColumn) getMpdrElementSource();
        }
        return null;
    }

}
