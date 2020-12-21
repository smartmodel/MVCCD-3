package mldr.interfaces;

import mcd.MCDElement;

public interface IMLDRElementWithSource {

    public MCDElement getMcdElementSource();
    public void setMcdElementSource(MCDElement mcdElementSource);

    public String getName();

}
