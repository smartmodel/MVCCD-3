package mdr.orderbuildnaming;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MDROrderBuildTargets {
    TABLE (Preferences.MDR_NAMING_TABLE, "tables"),
    TABLENN (Preferences.MDR_NAMING_TABLE_NN, "mdr.of.tables.nn"),
    TABLENNINDICE (Preferences.MDR_NAMING_TABLE_NN, "mdr.of.tables.nn"),
    COLUMNATTR (Preferences.MDR_NAMING_COLUMN_ATTR, "mdr.of.columns.attr"),
    COLUMNATTRSHORTNAME (Preferences.MDR_NAMING_COLUMN_ATTR, "mdr.of.columns.attr"),
    COLUMNPK (Preferences.MDR_NAMING_COLUMN_PK, "mdr.of.columns.pk"),
    COLUMNFKFROMENTITYNOIND(Preferences.MDR_NAMING_COLUMN_FK, "mdr.of.columns.fk"),
    COLUMNFKFROMENTITYIND (Preferences.MDR_NAMING_COLUMN_FK, "mdr.of.columns.fk"),
    COLUMNFKFROMENTITYSPECIALIZED (Preferences.MDR_NAMING_COLUMN_FK, "mdr.of.columns.fk"),
    COLUMNFKONEANCESTOR (Preferences.MDR_NAMING_COLUMN_FK, "mdr.of.columns.fk"),
    PK (Preferences.MDR_NAMING_PK, "mdr.of.constraints.pk"),
    PKNN (Preferences.MDR_NAMING_PK_NN, "mdr.of.tables.nn"),
    PKNNINDICE (Preferences.MDR_NAMING_PK_NN_INDICE, "mdr.of.tables.nn"),
    FK (Preferences.MDR_NAMING_FK, "mdr.of.constraints.fk"),
    FKWITHOUTROLE(Preferences.MDR_NAMING_FK, "mdr.of.constraints.fk"),
    UNIQUE(Preferences.MDR_NAMING_UNIQUE, "mdr.of.constraints.unique"),
    UNIQUEMAX30(Preferences.MDR_NAMING_UNIQUE, "mdr.of.constraints.unique"),
    CHECKCOLUMNDATATYPE(Preferences.MPDR_NAMING_CHECK_COLUMNDATATYPE, "mpdr.of.check.column.datatype"),
    CHECKCOLUMNDATATYPEMAX30(Preferences.MPDR_NAMING_CHECK_COLUMNDATATYPE_MAX30, "mpdr.of.check.column.datatype"),
    SEQUENCEPK(Preferences.MPDR_NAMING_SEQUENCE_PK, "mpdr.of.pk.column.pk"),
    TRIGGERTABLE(Preferences.MPDR_NAMING_TRIGGER_TABLE, "mpdr.of.trigger.table"),
    TRIGGERVIEW(Preferences.MPDR_NAMING_TRIGGER_VIEW, "mpdr.of.trigger.table"),
    FUNCTION(Preferences.MPDR_NAMING_FUNCTION, "mpdr.of.function.table"),
    PACKAGE(Preferences.MPDR_NAMING_PACKAGE, "mpdr.of.package.table"),
    VIEW(Preferences.MPDR_NAMING_VIEW, "mpdr.of.view.constraint");

    private final String name;
    private final String propertyOfTarget;

    MDROrderBuildTargets(String name, String propertyOfTarget) {
        this.name = name;
        this.propertyOfTarget = propertyOfTarget;
   }

    public String getName() {
        return name;
    }


    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MDROrderBuildTargets findByText(String text){
        for (MDROrderBuildTargets element: MDROrderBuildTargets.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public String getPropertyOfTarget() {
        return propertyOfTarget;
    }

    public String getMessageOfTarget() {
        return MessagesBuilder.getMessagesProperty(propertyOfTarget);
    }
}
