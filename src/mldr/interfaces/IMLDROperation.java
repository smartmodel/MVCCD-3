package mldr.interfaces;

import mdr.MDRParameter;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;

public interface IMLDROperation {

    public void setIteration(Integer iteration);
    public String getName();
    public MLDRParameter createParameter(IMDRParameter target);
    //public ArrayList<MDRParameter> getMDRParameters();
    public MDRParameter getParameter (IMDRParameter element);

}
