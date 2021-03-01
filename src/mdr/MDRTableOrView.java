package mdr;

import main.MVCCDElement;
import md.MDElement;
import mdr.interfaces.IMDRParameter;
import mdr.interfaces.IMDRElementWithIteration;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRTableOrView extends MDRElement implements IMDRElementWithIteration{

    //protected MDElement mdElementSource;
    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation


    public MDRTableOrView(ProjectElement parent) {
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

    public MDRContColumns getMDRContColumns() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRContColumns) {
                return (MDRContColumns) mvccdElement;
            }
        }
        return null;
    }


    public ArrayList<MDRColumn> getMDRColumns() {
        return getMDRContColumns().getMDRColumns();
    }


    public ArrayList<MDRColumn> getMDRColumnsPK() {
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();
        for (MDRColumn mdrColumn : getMDRColumns()){
            if (mdrColumn.isPk()) {
                resultat.add(mdrColumn);
            }
        }
        return resultat;
    }




    public MDRContConstraints getMDRContConstraints() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRContConstraints) {
                return (MDRContConstraints) mvccdElement;
            }
        }
        return null;
    }


    public ArrayList<MDRConstraint> getMDRConstraints(){
        return getMDRContConstraints().getMDRConstraints();
    }


    public boolean existColumn(MDRColumn mdrColumn){
        for (MDRColumn aMdrColumn : getMDRColumns()){
            if ( aMdrColumn == mdrColumn){
                return true;
            }
        }
        return false;
    }

    public boolean existIMDRParameter(IMDRParameter imdrParameter){
        for (IMDRParameter aIMDRParameter : getMDRColumns()){
            if ( aIMDRParameter == aIMDRParameter){
                return true;
            }
        }
        return false;
    }

}
