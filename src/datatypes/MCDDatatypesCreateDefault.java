package datatypes;

import main.MVCCDElement;
import main.MVCCDElementApplicationMDDatatypes;
import preferences.Preferences;

public class MCDDatatypesCreateDefault {

    private MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot;
    private MCDDatatype mcdDatatypeRoot;

    public MCDDatatypesCreateDefault(MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot) {
        this.mvccdDatatypeRoot = mvccdDatatypeRoot;
    }

    public MCDDatatype  create(){
        mcdDatatypeRoot = createMCDDatatype(
                mvccdDatatypeRoot,
                Preferences.MCDDATATYPE_ROOT_NAME,
                Preferences.MCDDATATYPE_ROOT_LIENPROG,
                true);

        // Boolean
        MCDDatatype bool = createMCDDatatype(
                mcdDatatypeRoot,
                Preferences.MCDDATATYPE_BOOLEAN_NAME,
                Preferences.MCDDATATYPE_BOOLEAN_LIENPROG,
                false);
        bool.setSizeMandatory(false);
        bool.setScaleMandatory(false);

        createText();
        createNumber();
        createTemporal();

        return mcdDatatypeRoot;
    }

    private void createTemporal() {
        //Temporel
        MCDDatatype temporal = createMCDDatatype(
                mcdDatatypeRoot,
                Preferences.MCDDATATYPE_TEMPORAL_NAME,
                Preferences.MCDDATATYPE_TEMPORAL_LIENPROG,
                true);
        temporal.setSizeMandatory(false);
        temporal.setScaleMandatory(false);

        MCDDatatype duration = createMCDDatatype(
                temporal,
                Preferences.MCDDATATYPE_DURATION_NAME,
                Preferences.MCDDATATYPE_DURATION_LIENPROG,
                false);

        MCDDatatype dateTime = createMCDDatatype(
                temporal,
                Preferences.MCDDATATYPE_DATETIME_NAME,
                Preferences.MCDDATATYPE_DATETIME_LIENPROG,
                false);

        MCDDatatype date = createMCDDatatype(
                dateTime,
                Preferences.MCDDATATYPE_DATE_NAME,
                Preferences.MCDDATATYPE_DATE_LIENPROG,
                false);

        MCDDatatype time = createMCDDatatype(
                dateTime,
                Preferences.MCDDATATYPE_TIME_NAME,
                Preferences.MCDDATATYPE_TIME_LIENPROG,
                false);

        MCDDatatype gYearMonth = createMCDDatatype(
                temporal,
                Preferences.MCDDATATYPE_GYEARMONTH_NAME,
                Preferences.MCDDATATYPE_GYEARMONTH_LIENPROG,
                false);

        MCDDatatype gYear = createMCDDatatype(
                temporal,
                Preferences.MCDDATATYPE_GYEAR_NAME,
                Preferences.MCDDATATYPE_GYEAR_LIENPROG,
                false);

        MCDDatatype gMonthDay = createMCDDatatype(
                temporal,
                Preferences.MCDDATATYPE_GMONTHDAY_NAME,
                Preferences.MCDDATATYPE_GMONTHDAY_LIENPROG,
                false);

        MCDDatatype gDay = createMCDDatatype(
                temporal,
                Preferences.MCDDATATYPE_GDAY_NAME,
                Preferences.MCDDATATYPE_GDAY_LIENPROG,
                false);

        MCDDatatype gMonth = createMCDDatatype(
                temporal,
                Preferences.MCDDATATYPE_GMONTH_NAME,
                Preferences.MCDDATATYPE_GMONTH_LIENPROG,
                false);

    }

    private void createNumber() {
        //Numérique
        MCDDatatype number = createMCDDatatype(
                mcdDatatypeRoot,
                Preferences.MCDDATATYPE_NUMBER_NAME,
                Preferences.MCDDATATYPE_NUMBER_LIENPROG,
                true);
        number.setSizeMandatory(true);
        number.setSizeMin(Preferences.MCDDATATYPE_NUMBER_SIZEMIN);

        createDecimal(number);
        createInteger(number);
        createMoney(number);
    }

    private void createInteger(MCDDatatype number) {
        MCDDatatype integer = createMCDDatatype(
                number,
                Preferences.MCDDATATYPE_INTEGER_NAME,
                Preferences.MCDDATATYPE_INTEGER_LIENPROG,
                false);
        integer.setSizeDefault(Preferences.MCDDATATYPE_INTEGER_SIZEDEFAULT);
        integer.setSizeMax(Preferences.MCDDATATYPE_INTEGER_SIZEMAX);
        integer.setScaleMandatory(false);

        MCDDatatype nonPositiveInteger = createMCDDatatype(
                integer,
                Preferences.MCDDATATYPE_NONPOSITIVEINTEGER_NAME,
                Preferences.MCDDATATYPE_NONPOSITIVEINTEGER_LIENPROG,
                false);

        MCDDatatype negativeInteger = createMCDDatatype(
                nonPositiveInteger,
                Preferences.MCDDATATYPE_NEGATIVEINTEGER_NAME,
                Preferences.MCDDATATYPE_NEGATIVEINTEGER_LIENPROG,
                false);

        MCDDatatype nonNegativeInteger = createMCDDatatype(
                integer,
                Preferences.MCDDATATYPE_NONNEGATIVEINTEGER_NAME,
                Preferences.MCDDATATYPE_NONNEGATIVEINTEGER_LIENPROG,
                false);

        MCDDatatype positiveInteger = createMCDDatatype(
                nonNegativeInteger,
                Preferences.MCDDATATYPE_POSITIVEINTEGER_NAME,
                Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG,
                false);

        MCDDatatype aid = createMCDDatatype(
                positiveInteger,
                Preferences.MCDDATATYPE_AID_NAME,
                Preferences.MCDDATATYPE_AID_LIENPROG,
                false);

    }

    private void createDecimal(MCDDatatype number) {

        MCDDatatype decimal = createMCDDatatype(
                number,
                Preferences.MCDDATATYPE_DECIMAL_NAME,
                Preferences.MCDDATATYPE_DECIMAL_LIENPROG,
                false);
        decimal.setSizeDefault(Preferences.MCDDATATYPE_DECIMAL_SIZEDEFAULT);
        decimal.setSizeMax(Preferences.MCDDATATYPE_DECIMAL_SIZEMAX);
        decimal.setScaleMandatory(true);
        decimal.setScaleDefault(Preferences.MCDDATATYPE_DECIMAL_SCALEDEFAULT);
        decimal.setScaleMin(Preferences.MCDDATATYPE_DECIMAL_SCALEMIN);
        decimal.setScaleMax(Preferences.MCDDATATYPE_DECIMAL_SCALEMAX);


        MCDDatatype nonPositiveDecimal = createMCDDatatype(
                decimal,
                Preferences.MCDDATATYPE_NONPOSITIVEDECIMAL_NAME,
                Preferences.MCDDATATYPE_NONPOSITIVEDECIMAL_LIENPROG,
                false);

        MCDDatatype negativeDecimal = createMCDDatatype(
                nonPositiveDecimal,
                Preferences.MCDDATATYPE_NEGATIVEDECIMAL_NAME,
                Preferences.MCDDATATYPE_NEGATIVEDECIMAL_LIENPROG,
                false);

        MCDDatatype nonNegativeDecimal = createMCDDatatype(
                decimal,
                Preferences.MCDDATATYPE_NONNEGATIVEDECIMAL_NAME,
                Preferences.MCDDATATYPE_NONNEGATIVEDECIMAL_LIENPROG,
                false);

        MCDDatatype positiveDecimal = createMCDDatatype(
                nonNegativeDecimal,
                Preferences.MCDDATATYPE_POSITIVEDECIMAL_NAME,
                Preferences.MCDDATATYPE_POSITIVEDECIMAL_LIENPROG,
                false);

    }

    private void createMoney(MCDDatatype number) {

        MCDDatatype money = createMCDDatatype(
                number,
                Preferences.MCDDATATYPE_MONEY_NAME,
                Preferences.MCDDATATYPE_MONEY_LIENPROG,
                false);
        money.setSizeDefault(Preferences.MCDDATATYPE_MONEY_SIZEDEFAULT);
        money.setSizeMax(Preferences.MCDDATATYPE_MONEY_SIZEMAX);
        money.setScaleMandatory(true);
        money.setScaleDefault(Preferences.MCDDATATYPE_MONEY_SCALEDEFAULT);
        money.setScaleMin(Preferences.MCDDATATYPE_MONEY_SCALEMIN);

        MCDDatatype nonPositiveMoney = createMCDDatatype(
                money,
                Preferences.MCDDATATYPE_NONPOSITIVEMONEY_NAME,
                Preferences.MCDDATATYPE_NONPOSITIVEMONEY_LIENPROG,
                false);

        MCDDatatype negativeMoney = createMCDDatatype(
                nonPositiveMoney,
                Preferences.MCDDATATYPE_NEGATIVEMONEY_NAME,
                Preferences.MCDDATATYPE_NEGATIVEMONEY_LIENPROG,
                false);

        MCDDatatype nonNegativeMoney = createMCDDatatype(
                money,
                Preferences.MCDDATATYPE_NONNEGATIVEMONEY_NAME,
                Preferences.MCDDATATYPE_NONNEGATIVEMONEY_LIENPROG,
                false);

        MCDDatatype positiveMoney = createMCDDatatype(
                nonNegativeMoney,
                Preferences.MCDDATATYPE_POSITIVEMONEY_NAME,
                Preferences.MCDDATATYPE_POSITIVEMONEY_LIENPROG,
                false);

    }


    private void createText() {
        // Texte
        MCDDatatype text = createMCDDatatype(
                mcdDatatypeRoot,
                Preferences.MCDDATATYPE_TEXT_NAME,
                Preferences.MCDDATATYPE_TEXT_LIENPROG,
                true);
        text.setSizeMandatory(true);
        text.setSizeMin(Preferences.MCDDATATYPE_TEXT_SIZEMIN);
        text.setScaleMandatory(false);

        MCDDatatype string = createMCDDatatype(
                text,
                Preferences.MCDDATATYPE_STRING_NAME,
                Preferences.MCDDATATYPE_STRING_LIENPROG,
                false);
        string.setSizeMax(Preferences.MCDDATATYPE_STRING_SIZEMAX);
        string.setSizeDefault(Preferences.MCDDATATYPE_STRING_SIZEDEFAULT);

        MCDDatatype normalizedString = createMCDDatatype(
                text,
                Preferences.MCDDATATYPE_NORMALIZEDSTRING_NAME,
                Preferences.MCDDATATYPE_NORMALIZEDSTRING_LIENPROG,
                false);
        normalizedString.setSizeMax(Preferences.MCDDATATYPE_NORMALIZEDSTRING_SIZEMAX);
        normalizedString.setSizeDefault(Preferences.MCDDATATYPE_NORMALIZEDSTRING_SIZEDEFAULT);

        MCDDatatype token = createMCDDatatype(
                text,
                Preferences.MCDDATATYPE_TOKEN_NAME,
                Preferences.MCDDATATYPE_TOKEN_LIENPROG,
                false);
        token.setSizeMax(Preferences.MCDDATATYPE_TOKEN_SIZEMAX);
        token.setSizeDefault(Preferences.MCDDATATYPE_TOKEN_SIZEDEFAULT);

        MCDDatatype word = createMCDDatatype(
                text,
                Preferences.MCDDATATYPE_WORD_NAME,
                Preferences.MCDDATATYPE_WORD_LIENPROG,
                false);
        word.setSizeMax(Preferences.MCDDATATYPE_WORD_SIZEMAX);
        word.setSizeDefault(Preferences.MCDDATATYPE_WORD_SIZEDEFAULT);

        MCDDatatype email = createMCDDatatype(
                word,
                Preferences.MCDDATATYPE_EMAIL_NAME,
                Preferences.MCDDATATYPE_EMAIL_LIENPROG,
                false);
        email.setSizeMin(Preferences.MCDDATATYPE_EMAIL_SIZEMIN);
        email.setSizeMax(Preferences.MCDDATATYPE_EMAIL_SIZEMAX);
        email.setSizeDefault(Preferences.MCDDATATYPE_EMAIL_SIZEDEFAULT);

        MCDDatatype httpURL = createMCDDatatype(
                word,
                Preferences.MCDDATATYPE_HTTPURL_NAME,
                Preferences.MCDDATATYPE_HTTPURL_LIENPROG,
                false);
        httpURL.setSizeMin(Preferences.MCDDATATYPE_HTTPURL_SIZEMIN);
        httpURL.setSizeMax(Preferences.MCDDATATYPE_HTTPURL_SIZEMAX);
        httpURL.setSizeDefault(Preferences.MCDDATATYPE_HTTPURL_SIZEDEFAULT);

        MCDDatatype xml = createMCDDatatype(
                string,
                Preferences.MCDDATATYPE_XML_NAME,
                Preferences.MCDDATATYPE_XML_LIENPROG,
                false);

    }


    private MCDDatatype createMCDDatatype (MVCCDElement parent , String name, String lienProg, boolean abstrait){
        // Vérifier l'unicité  à faire !
        MCDDatatype  mcdDatatype = new MCDDatatype(parent, name, lienProg, abstrait);
        return mcdDatatype;
    }

}
