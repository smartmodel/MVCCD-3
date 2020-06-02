package m;

import m.services.MRelEndService;
import mcd.MCDElement;
import mcd.MCDRelation;

public interface MRelEnd {

    public static final int FROM = 1 ;
    public static final int TO = 2 ;

    public static final int GEN = 3 ;
    public static final int SPEC = 4 ;


    public MCDRelation getMcdRelation();

    public void setMcdRelation(MCDRelation mcdRelation);

    public MCDElement getMcdElement();

    public void setMcdElement(MCDElement mcdElement);


}
