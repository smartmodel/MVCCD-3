package constraints;

import main.MVCCDElement;

public class Constraint extends MVCCDElement {
    private String lienProg;
    private String classTargetName;


    public Constraint(MVCCDElement parent, String name, String classTargetName) {
        super(parent, name);
        init(name, classTargetName);
    }

    public Constraint(MVCCDElement parent, String name, String lienProg, String classTargetName) {
        super(parent, name);
        init(lienProg, classTargetName);
    }

    private void init(String lienProg, String classTargetName){
        initLienProg(lienProg);
        initclassTargetName(classTargetName);
    }

    private void initclassTargetName(String classTargetName) {
        this.classTargetName = classTargetName;
    }

    private void initLienProg(String lienProg){

        this.lienProg = lienProg;
    }

    public String getLienProg() {
        return lienProg;
    }

    public String getClassTargetName() {
        return classTargetName;
    }


    @Override
    public String getNameTree() {
        return null;
    }
}
