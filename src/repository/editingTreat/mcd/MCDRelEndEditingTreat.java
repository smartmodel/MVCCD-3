package repository.editingTreat.mcd;

import m.interfaces.IMRelation;
import main.MVCCDElement;
import mcd.MCDRelEnd;
import repository.editingTreat.EditingTreat;

import java.awt.*;

public abstract class MCDRelEndEditingTreat extends EditingTreat {

    public boolean treatDelete(Window owner, MVCCDElement element) {

        IMRelation imRelation = ((MCDRelEnd) element).getImRelation();
        MCDRelationEditingTreat mcdRelationEditingTreat = new MCDRelationEditingTreat();
        return mcdRelationEditingTreat.treatDelete(owner, (MVCCDElement) imRelation);
    }
}
