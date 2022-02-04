package mpdr.interfaces;

import mldr.MLDRParameter;
import mpdr.MPDRParameter;

public interface IMPDROperation {

    public void setIteration(Integer iteration);
    public String getName();
    public MPDRParameter createParameter(MLDRParameter mldrParameter);

}
