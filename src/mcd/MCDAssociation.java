package mcd;

import main.MVCCDElement;
import mcd.services.MCDElementService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MCDAssociation extends MCDRelation{

    private  static final long serialVersionUID = 1000;


    public MCDAssociation(MCDElement parent) {

        super(parent);
    }

    public MCDAssociation(MCDElement parent, String name) {

        super(parent, name);
    }

    public MCDAssEnd getFrom() {
        return (MCDAssEnd) super.getA();
    }

    public void setFrom(MCDAssEnd from) {
        super.setA(from);
    }

    public MCDAssEnd getTo() {
        return (MCDAssEnd) super.getB();
    }

    public void setTo(MCDAssEnd to) {
        super.setB(to);
    }

    @Override
    public String getNameTree(){
        String resultat = "";

        MCDEntity entityFrom = getFrom().getMcdEntity();
        MCDEntity entityTo = getTo().getMcdEntity();

        MVCCDElement containerAssociation = this.getParent().getParent();


        MVCCDElement containerEntityFrom = entityFrom.getParent().getParent();
        MVCCDElement containerEntityTo = entityTo.getParent().getParent();

        boolean c1a = containerEntityFrom == containerAssociation;
        boolean c1b = containerEntityTo == containerAssociation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameEntityFrom = "";
        String nameEntityTo = "";

        if (r1){
            nameEntityFrom = entityFrom.getName();
            nameEntityTo = entityTo.getName();
        }
        
        if (r2){
            nameEntityFrom = entityFrom.getShortNameSmart();
            nameEntityTo = entityTo.getShortNameSmart();
        }

        if (r3){
            nameEntityFrom = entityFrom.getNamePath(MCDElementService.PATHSHORTNAME);
            nameEntityTo = entityTo.getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if (r4){
           nameEntityFrom = entityFrom.getShortNameSmartPath();
           nameEntityTo = entityTo.getShortNameSmartPath();
        }

        String namingAssociation ;
        if ((getFrom().getName() != null ) && (getTo().getName() != null)){
            namingAssociation = Preferences.MCD_NAMING_ASSOCIATION_ARROW_RIGHT +
                    getFrom().getName() +
                    Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR  +
                    getTo().getName() +
                    Preferences.MCD_NAMING_ASSOCIATION_ARROW_LEFT;
        } else {
            namingAssociation = Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR +
                    this.getName() + Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;
        }
        resultat = nameEntityFrom + namingAssociation + nameEntityTo;
        return resultat;
    }


}
