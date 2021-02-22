package mldr.interfaces;

import mcd.MCDElement;
import md.interfaces.IMDElementWithSource;

public interface IMLDRElementWithSource extends IMDElementWithSource {

    public MCDElement getMcdElementSource();
    public void setMcdElementSource(MCDElement mcdElementSource);


}
