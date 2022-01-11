package mpdr.tapis.oracle;

import mpdr.interfaces.IMPDRTriggerType;

public enum MPDROracleTriggerType implements IMPDRTriggerType {

    BIR("BIR", "bir.txt"),
    BUR("BUR", "bur.txt"),
    BIU("BIU", "biu.txt"),
    BDR("BDR", "bdr.txt"),
    AIUD("AIUD",  "aiud.txt"),
    IOINS_ASSNNNONORIENTD("IOINS", "ioins_AssNNNonOriented.txt"),
    IODEL_ASSNNNONORIENTD("IODEL", "iodel_AssNNNonOriented.txt"),
    IOUPD_ASSNNNONORIENTD("IOUPD", "ioupd_AssNNNonOriented.txt");


    private String name;
    private String templateFileName;


    private MPDROracleTriggerType(String name, String templateFileName) {
        this.name = name;
        this.templateFileName = templateFileName;
    }

    public String getName() {
        return name;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public MPDROracleTriggerType[] getAll() {
        return values();
    }


}
