package mdr;

import main.MVCCDElement;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDContConstraints;
import md.MDElement;
import mdr.interfaces.IMDRElementWithSource;
import mdr.interfaces.IMDRParameter;
import project.ProjectElement;
import utilities.Trace;

import java.util.ArrayList;

public abstract class MDRTableOrView extends MDRElement implements IMDRElementWithSource {

    //protected MDElement mdElementSource;
    private  static final long serialVersionUID = 1000;

    public MDRTableOrView(ProjectElement parent) {
        super(parent);
    }

    public ArrayList<MDRColumn> getMDRColumns() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRContColumns) {
                MDRContColumns mdrContColumns = (MDRContColumns ) mvccdElement;
                return mdrContColumns.getMDRColumns();
            }
        }
        return new ArrayList<MDRColumn>();
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

    public MDRContColumns getMDRContColumns() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRContColumns) {
                return (MDRContColumns) mvccdElement;
            }
        }
        return null;
    }


    public ArrayList<MDRConstraint> getMDRConstraints() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRContConstraints) {
                MDRContConstraints mdrContConstraints = (MDRContConstraints ) mvccdElement;
                return mdrContConstraints.getMDRConstraints();
            }
        }
        return new ArrayList<MDRConstraint>();
    }

    public MDRContConstraints getMDRContConstraints() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRContConstraints) {
                return (MDRContConstraints) mvccdElement;
            }
        }
        return null;
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
