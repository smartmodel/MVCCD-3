package connections;

import main.MVCCDElement;
import org.apache.commons.lang.StringUtils;
import repository.editingTreat.EditingTreat;

public abstract class ConnectionsDB extends ConElement {


    public ConnectionsDB(MVCCDElement parent, String name, ConDB conDB) {
        super(parent, name, conDB);
        setName(name);
    }


    public void setName(String name) {
        if (StringUtils.isEmpty(lienProg)){
            lienProg = getConDB().getLienProg() ;
        }
        super.setName(name);
    }


    @Override
    public String getNamePath() {
        return null;
    }


    public abstract EditingTreat getConConnectionEditingTreat();
}
