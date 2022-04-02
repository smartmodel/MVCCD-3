package mdr;

import constraints.ConstraintService;
import m.interfaces.IMClass;
import main.MVCCDElement;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithIteration;
import mdr.interfaces.IMDRParameter;
import mdr.services.MDRTableOrViewService;
import project.ProjectElement;
import stereotypes.StereotypeService;

import java.util.ArrayList;

public abstract class MDRTableOrView extends MDRElement implements IMDRElementWithIteration, IMClass, IMDRElementNamingPreferences {

    //protected MDElement mdElementSource;
    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation

    public MDRTableOrView(ProjectElement parent, int id) {
        super(parent, id);
    }

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

    public ArrayList<String> getMDRColumnsNames() {
        ArrayList<String> resultat = new ArrayList<String>();
        for (MDRColumn mdrColumns : getMDRColumns()){
            resultat.add(mdrColumns.getName());
        }
        return resultat;
    }

    public ArrayList<MDRColumn> getMDRColumnsSortDefault(){
        return getMDRContColumns().getMDRColumnsSortDefault();
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

    @Override
    public String getStereotypesInBox() {
        return StereotypeService.getUMLNamingInBox(getStereotypes());
    }

    @Override
    public String getStereotypesInLine() {
        return StereotypeService.getUMLNamingInLine(getStereotypes());
    }

    @Override
    public String getConstraintsInBox() {
        return ConstraintService.getUMLNamingInBox(getConstraints());
    }

    @Override
    public String getConstraintsInLine() {
        return ConstraintService.getUMLNamingInLine(getConstraints());
    }

    public String getListColumnsAsString(String separator,
                                         boolean withTableOrView){
        return MDRTableOrViewService.getListColumnsAsString(this, separator, withTableOrView);
    }

}
