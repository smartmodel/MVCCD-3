package m.interfaces;

import main.MVCCDElement;

public interface IMRelation {

    public MVCCDElement getParent();

    public String getName();
    public String getShortName();

    public IMRelEnd getA();

    public void setA(IMRelEnd a);

    public IMRelEnd getB();

    public void setB(IMRelEnd b);

    public void removeInParent() ;

}
