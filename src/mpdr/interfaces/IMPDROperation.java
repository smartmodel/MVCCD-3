package mpdr.interfaces;

import mdr.MDRParameter;
import mldr.MLDRParameter;
import mpdr.MPDRParameter;

import java.util.ArrayList;

public interface IMPDROperation {

    public void setIteration(Integer iteration);
    public String getName();
    public MPDRParameter createParameter(MLDRParameter mldrParameter);
    public ArrayList<MDRParameter> getMDRParameters();

}
