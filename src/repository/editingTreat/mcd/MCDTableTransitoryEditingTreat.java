package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import repository.editingTreat.EditingTreat;

import java.awt.*;

public abstract class MCDTableTransitoryEditingTreat extends EditingTreat {

    public boolean treatUpdate(Window owner, MVCCDElement element) {
        boolean updated = super.treatUpdate(owner, element);
        if (updated) {
            MVCCDManager.instance().replaceChildsInRepository(element);
        }
        return updated;
    }

    public MVCCDElement treatNew(Window owner, MVCCDElement parent) {
        MVCCDElement newElement = super.treatNew(owner, parent);
        if (newElement != null) {
            MVCCDManager.instance().replaceChildsInRepository(newElement);
        }
        return newElement;
    }
}
