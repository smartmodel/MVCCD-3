package mpdr.tapis.oracle;

import mpdr.tapis.MPDRDynamicCode;
import mpdr.tapis.interfaces.IMPDRDynamicCodeType;

public class MPDROracleDynamicCode extends MPDRDynamicCode {

    public String getDynamiqueCode(IMPDRDynamicCodeType dynamicCodeType){
        String dynamicCode = "";
        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.TABLE_DEP_JOIN_PARENT.getKey())){
            //dynamicCode = tableDepJoinParent();
        }
        /*
        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.COLUMNS_UPPERCASE.getKey())){
            dynamicCode = columnsUppercase();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.COLUMNS_TYPE_CHECK.getKey())){
            dynamicCode = columnsTypeCheck();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.COLUMNS_FROZEN.getKey())){
            dynamicCode = columnsFrozen();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.COLUMN_TARGET_LIEN_PROG.getKey())){
            dynamicCode = columnTargetLienProg();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.COLUMNS_PEA.getKey())){
            dynamicCode = columnsPEA();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.ALL_COLUMNS_NAME.getKey())){
            dynamicCode = columnsName("SCOPE_ALL");
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.ALL_COLUMNS_VALUE.getKey())){
            dynamicCode = columnsValue("SCOPE_ALL");
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.PK_COLUMNS_NAME.getKey())){
            dynamicCode = columnsName("SCOPE_PK");
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.PK_COLUMNS_VALUE.getKey())){
            dynamicCode = columnsValue("SCOPE_PK");
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.JN_COLUMNS_NAME.getKey())){
            dynamicCode = columnsJnName();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.JN_COLUMNS_VALUE.getKey())){
            dynamicCode = columnsJnValue();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.ORDERED_INS.getKey())){
            dynamicCode = orderedsIns();
        }

        if (dynamicCodeType.getKey().equals(MPDROracleDynamicCodeType.COLUMN_DEFAULT_VALUE.getKey())){
            dynamicCode = columnsDefaultValue();
        }

         */


        return dynamicCode;
    }


}
