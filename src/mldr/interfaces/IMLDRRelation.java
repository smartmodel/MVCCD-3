package mldr.interfaces;

import mcd.MCDElement;
import md.MDElement;

public interface IMLDRRelation extends IMLDRElement {

    public String getName();
    public MCDElement getMcdElementSource();

    public void setMcdElementSource(MCDElement mcdElementSource) ;

    public MDElement getMdElementSource();

}
