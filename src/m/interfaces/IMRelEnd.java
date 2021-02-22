package m.interfaces;

import m.MElement;
import m.services.MRelEndService;
import mcd.MCDElement;
import mcd.MCDRelation;

public interface IMRelEnd {

    public IMRelation getImRelation() ;

    public void setImRelation(IMRelation imRelation) ;

    public MElement getmElement() ;

    public void setmElement(MElement mElement) ;

}
