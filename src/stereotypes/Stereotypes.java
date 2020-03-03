package stereotypes;

import main.MVCCDElement;

import java.util.ArrayList;

public class Stereotypes extends MVCCDElement {
    public Stereotypes(MVCCDElement parent) {
        super(parent);
    }

    public Stereotypes(MVCCDElement parent, String name) {
        super(parent, name);
    }

    public ArrayList<Stereotype> getStereotypesByClassName(String className){
        ArrayList<Stereotype> resultat = new  ArrayList<Stereotype>();
        for (MVCCDElement mvccdElement : this.getChilds()){
            if (mvccdElement instanceof Stereotype){
                Stereotype stereotype = (Stereotype) mvccdElement;
                if (stereotype.getClassTargetName().equals(className)){
                    resultat.add(stereotype);
                }
            }

        }
        return resultat;
    }

    public Stereotype getStereotypeByLienProg(String className, String lienProg){
        for (Stereotype stereotype : getStereotypesByClassName(className)){
            if (stereotype.getLienProg().equals(lienProg)){
                return stereotype;
            }
        }
        return null;
    }

    public ArrayList<Stereotype> getStereotypeByClassNameAndNames( String className,
                                                                   ArrayList<String> names){
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();
        ArrayList<Stereotype> stereotypesClassName = getStereotypesByClassName(className);
        for (Stereotype stereotype : stereotypesClassName){
            for (String name : names){
                if (stereotype.getName().equals(name)) {
                    resultat.add(stereotype);
                }
            }
        }
        return resultat;
    }
}
