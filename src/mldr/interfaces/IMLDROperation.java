package mldr.interfaces;

import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;

public interface IMLDROperation {

    public void setIteration(Integer iteration);
    public String getName();
    public MLDRParameter createParameter(IMDRParameter target);

}
