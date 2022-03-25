grammar OCLMCD;


@header
{
import javax.xml.datatype.*;
import javax.xml.datatype.Duration;
import java.util.*;
import java.time.*;
import java.time.format.*;
}

@parser::members {
	private int vDataTypeGroup, vConstraintType, vDataTypeNum;
	private String vDataType, vAttributeName, vClassName, vConstraintName, vMessageError;
	private HashSet<Integer> vElements = new HashSet<Integer>();

	private boolean checkNumLiteral (String type, String current){
		switch (type){
			case "decimal" 				: vDataTypeNum = DECIMALTYPE; return (current.matches("(0\\.0+|-?([1-9][0-9]*\\.[0-9]+|0\\.[0-9]*[1-9][0-9]*))"))? true : false ;
			case "nonPositiveDecimal"	: vDataTypeNum = DECIMALTYPE; return (current.matches("(0\\.0+|-([1-9][0-9]*\\.[0-9]+|0\\.[0-9]*[1-9][0-9]*))"))? true : false ;
			case "negativeDecimal" 		: vDataTypeNum = DECIMALTYPE; return (current.matches("-([1-9][0-9]*\\.[0-9]+|0\\.[0-9]*[1-9][0-9]*)"))? true : false ;
			case "nonNegativeDecimal"	: vDataTypeNum = DECIMALTYPE; return (current.matches("([1-9][0-9]*\\.[0-9]+|0\\.[0-9]+)"))? true : false ;
			case "positiveDecimal" 		: vDataTypeNum = DECIMALTYPE; return (current.matches("([1-9][0-9]*\\.[0-9]+|0\\.[0-9]*[1-9][0-9]*)"))? true : false ;
			case "integer" 				: vDataTypeNum = INTEGERTYPE; return (current.matches("(0|-?[1-9][0-9]*)"))? true : false ;
			case "nonPositiveInteger"	: vDataTypeNum = INTEGERTYPE; return (current.matches("(0|-[1-9][0-9]*)"))? true : false ;
			case "negativeInteger"		: vDataTypeNum = INTEGERTYPE; return (current.matches("-[1-9][0-9]*"))? true : false ;
			case "nonNegativeInteger"	: vDataTypeNum = INTEGERTYPE; return (current.matches("(0|[1-9][0-9]*)"))? true : false ;
			case "positiveInteger" 		: vDataTypeNum = INTEGERTYPE; return (current.matches("[1-9][0-9]*"))? true : false ;
			case "money"	 			: vDataTypeNum = MONEYTYPE;   return (current.matches("(0\\.00|-?([1-9]([0-9]{1,2})?('[0-9]{3})*\\.[0-9]{2}|0\\.([0-9][1-9]|[1-9][0-9])))"))? true : false ;
			case "nonPositiveMoney"		: vDataTypeNum = MONEYTYPE;   return (current.matches("(0\\.00|-([1-9]([0-9]{1,2})?('[0-9]{3})*\\.[0-9]{2}|0\\.([0-9][1-9]|[1-9][0-9])))"))? true : false ;
			case "negativeMoney"	 	: vDataTypeNum = MONEYTYPE;   return (current.matches("-([1-9]([0-9]{1,2})?('[0-9]{3})*\\.[0-9]{2}|0\\.([0-9][1-9]|[1-9][0-9]))"))? true : false ;
			case "nonNegativeMoney"		: vDataTypeNum = MONEYTYPE;   return (current.matches("([1-9]([0-9]{1,2})?('[0-9]{3})*\\.[0-9]{2}|0\\.[0-9]{2})"))? true : false ;
			case "positiveMoney"	 	: vDataTypeNum = MONEYTYPE;   return (current.matches("([1-9]([0-9]{1,2})?('[0-9]{3})*\\.[0-9]{2}|0\\.([0-9][1-9]|[1-9][0-9]))"))? true : false ;
			default						: return false;
		}
	}

	private boolean checkCurrencyCode (String currencyCode){
		try{
			Currency c = Currency.getInstance(currencyCode);
			return true;
		} catch (IllegalArgumentException e) {
			if(vConstraintType == INV){
				vMessageError = "- Le code monétaire >"+currencyCode+"< utilisé dans l'invariant >"+vConstraintName+"< lié à l'entité >"+vClassName+"< est inconnu.";
			} else {
				vMessageError = "- Le code monétaire >"+currencyCode+"< utilisé dans une contrainte OCL de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< est inconnu.";
			}
			return false;
		}
	}

	private boolean checkNumberTypeDirection(int ruleDirection, String input){
		boolean correct = true;
		if(vConstraintType == INIT){
			if(ruleDirection == RULE_decliteral && vDataTypeNum == MONEYTYPE){
				vMessageError="- Le code monétaire est requis pour la valeur initiale >"+input+"< de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< car le type d'attribut est >"+vDataType+"<.";
				correct =  false;
			} else if (ruleDirection == RULE_monliteral && vDataTypeNum == DECIMALTYPE){
				vMessageError ="- Le code monétaire est refusé dans la valeur initiale >"+input+"< de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< car le type d'attribut est >"+vDataType+"<.";
				correct = false;
			}
		} else {
			if(checkNumLiteral("decimal",input)){
				correct = true;
			} else if(checkNumLiteral("money",input)){
				correct =  true;
			} else if (checkNumLiteral("integer",input)){
				correct = true;
			}else {
				if(vConstraintType == DERIVE){
					vMessageError="- La valeur >"+input+"< contenu dans la valeur dérivé de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< n'est pas conforme à la logique mathématique.";
				}else{
					vMessageError="- La valeur >"+input+"< contenu dans l'invariant >"+vConstraintName+"< lié à l'entité >"+vClassName+"< n'est pas conforme à la logique mathématique.";
				}
				correct = false;
			}
		}
		return correct;
	}

	private boolean checkDatLiteral(String type, String current){
		//System.out.println("La valeur type : "+type+". Et la valeur current : "+current);
		switch (type){
			case "duration"		: try {
									Duration dur = DatatypeFactory.newInstance().newDuration(current);
									return true;
							  	  } catch (Exception e){ //DatatypeConfigurationException et IllegalArgumentException
									return false;
							  	  }
			case "gYearMonth"	:try {
									YearMonth.parse(current, DateTimeFormatter.ofPattern("MMMM uuuu").withResolverStyle(ResolverStyle.STRICT));
									return true;
								 } catch (DateTimeParseException e) {
									return false;
								 }
			case "gYear"		:try {
									Year.parse(current, DateTimeFormatter.ofPattern("uuuu").withResolverStyle(ResolverStyle.STRICT));
									return true;
								 } catch (DateTimeParseException e) {
									return false;
								 }
			case "gMonthDay"   :try {
									MonthDay.parse(current, DateTimeFormatter.ofPattern("dd MMMM").withResolverStyle(ResolverStyle.STRICT));
									return true;
								 } catch (DateTimeParseException e) {
									return false;
								 }
			case "gDay"			:return (current.matches("(0?[1-9]|[12][0-9]|3[01])"))? true : false ;

			case "gMonth"		:try {
									DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM").withResolverStyle(ResolverStyle.STRICT);
									format.parse(current);
									return true;
								 } catch (DateTimeParseException e) {
									return false;
								 }
			case "dateTime"		: String [] currents = current.split(" "); return (checkDate(currents[0]) && checkTime(currents[1]));
			case "date"			: return checkDate(current);
			case "time"			: return checkTime(current);
			default				: return false;
		}
	}

	private boolean checkTime(String current){
		//System.out.println("Time : "+current);
		try {
				if (current.length() <= 2){
					LocalTime.parse(current, DateTimeFormatter.ofPattern("HH").withResolverStyle(ResolverStyle.STRICT));
				} else if (current.length() > 2 && current.length() <=5){
					LocalTime.parse(current, DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT));
				} else if (current.length() > 5){
					LocalTime.parse(current, DateTimeFormatter.ofPattern("HH:mm:ss").withResolverStyle(ResolverStyle.STRICT));
				}
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
	}

	private boolean checkDate(String current){
		//System.out.println("Date : "+current);
		try {
				LocalDate.parse(current, DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT));
				return true;
			 } catch (DateTimeParseException e) {
				return false;
			 }
	}

	private void addCheckedElementRuleValueByExpression(String value, int elementRule){
		//System.out.println("La valeur de vDataTypeGroup "+vDataTypeGroup);
		switch (vDataTypeGroup){
			case NUMBERTYPE	:
					switch (vConstraintType){
						case INIT :
							if(elementRule == RULE_numliteral){
								if(checkNumLiteral(vDataType,value)){
									addElementRule(elementRule);
								} else {
									notifyErrorListeners("La valeur initiale >"+value+"< de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< ne correspond pas au type >"+vDataType+"<.");
								}
							}
						default : addElementRule(elementRule);
					}
			case DATETYPE	:
					switch (vConstraintType){
						case INIT :
							if(elementRule == RULE_datliteral){
								if(checkDatLiteral(vDataType,value)){
									addElementRule(elementRule);
								} else {
									notifyErrorListeners("La valeur initiale >"+value+"< de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< ne correspond pas au type >"+vDataType+"<.");
								}
							}
						default : addElementRule(elementRule);
					}
			case BOOLTYPE : addElementRule(elementRule);
		}
	}

	private void addElementRule(int elementRule){
		vElements.add(elementRule);
	}

	private boolean checkElementRuleInExpressionByConstraint(int dataTypeGroup){
		vDataTypeGroup = dataTypeGroup;
		return checkElementRuleInExpressionByConstraint();
	}

	private boolean checkElementRuleInExpressionByConstraint(){
		//System.out.println("La taille du HashSet : "+vElements.size());
		//System.out.println("Les valeurs contenu dans le tableau : "+vElements);
		switch (vDataTypeGroup){
			case NUMBERTYPE	:
					switch (vConstraintType){
						case DERIVE :
								if (vElements.contains(RULE_modelPropertyCallExprMethods)) {
									vMessageError = "Une seule méthode peut être appelée dans un attribut dérivé de type numérique, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() == 1)? true : false;
								} else if (vElements.contains(RULE_numliteral) && vElements.contains(RULE_mathOp)){ //100 * 100
									vMessageError = "Les valeurs numériques seules ne sont pas autorisées dans un attribut dérivé de type numérique, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() > 2)? true : false;
								} else if (vElements.contains(RULE_numliteral)){ //100
									vMessageError = "Une valeur numérique seule n'est pas autorisée dans un attribut dérivé de type numérique, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() > 1)? true : false;
								} else {
									return true;
								}
						case INIT :
								if (vElements.contains(RULE_numliteral)){
									vMessageError = "La valeur initiale n'accepte qu'une valeur numérique par défaut, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() == 1)? true : false;
								} else {
									vMessageError = "La valeur initiale n'accepte qu'une valeur numérique par défaut, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return false;
								}
						default : return true;
					}
			case DATETYPE	:
					switch (vConstraintType){
						case INIT :
								if (vElements.contains(RULE_nowDefinition)){
									vMessageError = "La méthode >now()< est autorisée seulement si elle est seule dans l'expression d'une valeur initial de type temporel, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() == 1)? true : false;
								} else {
									return true;
								}
						case DERIVE :
								if (vElements.contains(RULE_datliteral) && vElements.contains(ADD) && vElements.contains(SUB)){
									vMessageError = "Les valeurs temporelles seules ne sont pas autorisées dans un attribut dérivé de type temporel, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() > 3)? true : false;
								} else if ((vElements.contains(RULE_datliteral) && vElements.contains(ADD)) || (vElements.contains(RULE_datliteral) && vElements.contains(SUB))){
									vMessageError = "Les valeurs temporelles seules ne sont pas autorisées dans un attribut dérivé de type temporel, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() > 2)? true : false;
								} else if (vElements.contains(RULE_datliteral)) {
									vMessageError = "Une valeurs temporelle seule n'est pas autorisée dans un attribut dérivé de type temporel, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() > 1)? true : false;
								} else if (vElements.contains(RULE_nowDefinition)){
									vMessageError = "La méthode >now()< n'est pas autorisée dans un attribut dérivé de type temporel, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return false;
								} else {
									return true;
								}
						default : return false;
					}
			case BOOLTYPE :
					switch (vConstraintType){
						case INV :
								if (vElements.contains(NOT) && vElements.contains(RULE_numliteral)){
									vMessageError = "Les valeurs numériques seules ne sont pas autorisées dans une expression booléenne quel que soit la contrainte OCL, l'erreur se trouve dans l'invariant >"+vConstraintName+"< lié à l'entité >"+vClassName+"<.";
									return (vElements.size() > 2)? true : false;
								} else if (vElements.contains(RULE_numliteral)) {
									vMessageError = "Une valeur numérique seule n'est pas autorisée dans une expression booléenne quel que soit la contrainte OCL, l'erreur se trouve dans l'invariant >"+vConstraintName+"< lié à l'entité >"+vClassName+"<.";
									return (vElements.size() > 1)? true : false;
								} else {
									return true;
								}
						case INIT :
								if (vElements.contains(RULE_boolliteral)){
									vMessageError = "Une valeur >True< ou >False< est attendue dans une valeur initiale de type booléenne, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() == 1)? true : false;
								} else {
									vMessageError = "Une valeur >True< ou >False< est attendue dans une valeur initiale de type booléenne, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return false;
								}
						case DERIVE :
								if (vElements.contains(NOT) && vElements.contains(RULE_numliteral)){
									vMessageError = "Les valeurs numériques seules ne sont pas autorisées dans une expression booléenne quel que soit la contrainte OCL, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() > 2)? true : false;
								} else if (vElements.contains(RULE_numliteral)) {
									vMessageError = "Les valeurs numériques seules ne sont pas autorisées dans une expression booléenne quel que soit la contrainte OCL, l'erreur se trouve dans l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"<.";
									return (vElements.size() > 1)? true : false;
								} else {
									return true;
								}
						default : return true;
					}
			default : return false;
		}
	}
}


/* SYNTAX RULES */
textInput						: classifierContext ;
classifierContext				: 'context' className (attributeContext | invariantDefinition | defDefinition);
attributeContext				: '::' attributeName ':' dataType (initialDefinition | deriveDefinition);

invariantDefinition				: t='inv' {vConstraintType = $t.type;} invName ':' boolExpression {checkElementRuleInExpressionByConstraint(BOOLTYPE)}?<fail={vMessageError}>
								| 'inv' ':' boolExpression {notifyErrorListeners("L'invariant lié à l'entité >"+vClassName+"< doit être nommé.");}
								| 'inv' invName ':' {notifyErrorListeners("Une expression est manquante dans l'invariant >"+$invName.text+"<.");}
								| 'inv' invName ':' (.+?) {notifyErrorListeners("L'expression de l'invariant >"+$invName.text+"< n'est pas booléenne.");}
								| (.+?)? invName ':' boolExpression {notifyErrorListeners("Contrainte liée à l'entité >"+vClassName+"<. Un mot clé (inv, init, derive) doit être indiqué.");}
								;

initialDefinition				: t='init' {vConstraintType = $t.type;} ':' oclExpression {checkElementRuleInExpressionByConstraint()}?<fail={vMessageError}>
								| 'init' ':' {notifyErrorListeners("L'expression de la valeur initial de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< est absente");}
								;

deriveDefinition				: t='derive' {vConstraintType = $t.type;} ':' oclExpression {checkElementRuleInExpressionByConstraint()}?<fail={vMessageError}>
								| 'derive' ':' {notifyErrorListeners("L'expression de l'attribut dérivé >"+vAttributeName+"< de l'entité >"+vClassName+"< est absente");}
								| (.+?)? ':' oclExpression {notifyErrorListeners("Contrainte liée à l'entité >"+vClassName+"<. Un mot clé (inv, init, derive) doit être indiqué.");}
								;

defDefinition					: 'def' simpleName? ':' variableDeclaration ;

oclExpression					: {vDataTypeGroup == BOOLTYPE}? 	boolExpression		#initBoolExpression
								| {vDataTypeGroup == NUMBERTYPE}? 	decimalExpression	#initDecimalExpression
								| {vDataTypeGroup == DATETYPE}? 	dateTimeExpression	#initDateTimeExpression
								| {vConstraintType == DERIVE}? .+? {notifyErrorListeners("L'expression de l'attribut dérivé >"+vAttributeName+"< de l'entité >"+vClassName+"< ne correspond pas au type de l'attribut");} #initNone
								| {vConstraintType == INIT}? .+? {notifyErrorListeners("L'expression de la valeur initial de l'attribut >"+vAttributeName+"< de l'entité >"+vClassName+"< ne correspond pas au type de l'attribut");} #initNone
								;

derExpression					: oclExpression ;

dataType						: t=(BOOLTYPE | NUMBERTYPE | DATETYPE) {vDataTypeGroup = $t.type; vDataType = $t.text;}
								| .+? {notifyErrorListeners("Le type de l'attribut >"+vAttributeName+"< de l'entité contexte >"+vClassName+"< est inconnu");}
								;

boolExpression					: '(' boolExpression ')' 																						#boolNone
								| boolExpression		{addElementRule(RULE_boolOp);}							boolOp boolExpression			#boolOperation
								| boolExpression		{addElementRule(RULE_relOp);}							relOp boolExpression			#boolcomparatorOp
								| 						{addElementRule(NOT);}									NOT boolExpression				#boolNot
								| 						{addElementRule(RULE_modelPropertyCallExprBoolean);} 	modelPropertyCallExprBoolean	#boolNone
								| 						{addElementRule(RULE_numliteral);} 						numliteral						#boolNone
								| 						{addElementRule(RULE_instantiation);} 					instantiation					#boolNone
								| 						{addElementRule(RULE_modelPropertyCallExpr);}			modelPropertyCallExpr			#boolNone
								| 						{addElementRule(RULE_boolliteral);} 					boolliteral						#boolNone
								| modelPropertyCallExpr (EQ | NEQ) NULL																			#boolNone
								;

decimalExpression 				: '(' decimalExpression ')'
								| decimalExpression		{addElementRule(RULE_mathOp);}												mathOp decimalExpression
								| 						{addCheckedElementRuleValueByExpression($start.getText(),RULE_numliteral);} numliteral
								| 						{addElementRule(RULE_modelPropertyCallExpr);} 								modelPropertyCallExpr
								| 						{addElementRule(RULE_modelPropertyCallExprDecimal);} 						modelPropertyCallExprDecimal
								| 						{addElementRule(RULE_modelPropertyCallExprMethods);} 						modelPropertyCallExprMethods
								| 						{addElementRule(RULE_instantiation);} 										instantiation
								;

dateTimeExpression				: '(' dateTimeExpression ')'
								| dateTimeExpression	{addElementRule(ADD);} 														ADD dateTimeExpression
								| dateTimeExpression 	{addElementRule(SUB);}														SUB dateTimeExpression
								|						{addCheckedElementRuleValueByExpression($start.getText(),RULE_datliteral);}	datliteral
								| 						{addElementRule(RULE_nowDefinition);} 										nowDefinition
								| 						{addElementRule(RULE_modelPropertyCallExpr);}								modelPropertyCallExpr
								;

modelPropertyCallExpr			: roleOrAssocEntity '.' modelPropertyCallExpr
								| roleOrAssocEntity CALL modelPropertyCallExpr
								| paramAttributeName
								;

modelPropertyCallExprBoolean	: modelPropertyCallExpr ':' 'boolean';
modelPropertyCallExprDecimal	: modelPropertyCallExpr ':' DECIMAL;

modelPropertyCallExprMethods 	: modelPropertyCallExpr CALL groupOp
							 	| roleOrAssocEntity('.'roleOrAssocEntity)* CALL groupOpParam
							 	| ensOp CALL modelPropertyCallExpr
							 	| ensOp
							 	;

ensOp							: collectDefinition | iterateDefinition | inclDefinition | groupOp ;	/* Toute opération qui s'applique à un ensemble */

groupOp							: {vDataTypeGroup == NUMBERTYPE && vConstraintType == DERIVE}? (averageDefinition | sumDefinition)
								| sizeDefinition
								| isEmptyDefinition
								| notEmptyDefinition
								; /* Toute opération qui retourne un objet et non un ensemble */

groupOpParam					: {vDataTypeGroup == NUMBERTYPE && vConstraintType == DERIVE}? (averageDefinitionParam | sumDefinitionParam )
								| sizeDefinitionParam;

collectDefinition				: COLLECT BRACKETSTART attributeName BRACKETSTOP ;
iterateDefinition				: ITERATE BRACKETSTART iterateParams BRACKETSTOP ;
iterateParams					: variableDeclaration ';' variableDeclaration '|' blocCodeExecution ;
blocCodeExecution				: instruction+ ;
instruction						: ifInstruction | callInstruction ;
callInstruction					: modelPropertyCallExpr ';' ;
ifInstruction					: 'if' boolExpression 'then' blocCodeExecution ('else' blocCodeExecution)? 'endif' ;
variableDeclaration				: simpleName (':' typeDefinition)? (EQ oclExpression)? ;
typeDefinition					: primitiveType| objectTypeDefinition ;
objectTypeDefinition			: colTypeDefinition | className ;
colTypeDefinition				: colType BRACKETSTART className BRACKETSTOP ;
instantiation					: colInstantiation ;
colInstantiation				: colType BRACESTART BRACESTOP ;
colType							: TSET ;
roleOrAssocEntity				: WORD ;
className						: c=WORD {vClassName = $c.text;};
attributeName					: a=WORD {vAttributeName = $a.text;};
paramAttributeName				: WORD ;
simpleName						: WORD ;
invName							: i=WORD {vConstraintName = $i.text;} ;
primitiveType					: FLOAT ;
literal							: boolliteral | numliteral | datliteral ;
boolliteral						: BOOLEAN;
datliteral						: gDatliteral | durliteral | tdatliteral;
durliteral 						: DURATION;
gDatliteral						: GMONTH | INT /*ANNEE OU JOUR */ | GYEARMONTH | GMONTHDAY;
tdatliteral						: TIME | DATE | DATETIME;

numliteral						: intliteral | decliteral | monliteral;
intliteral						: i=INT {checkNumberTypeDirection(RULE_intliteral, $i.text)}?<fail={vMessageError}>;
decliteral						: d=DEC {checkNumberTypeDirection(RULE_decliteral, $d.text)}?<fail={vMessageError}>;
monliteral						: m=DEC {checkNumberTypeDirection(RULE_monliteral, $m.text)}?<fail={vMessageError}> c=CURRENCYCODE {checkCurrencyCode($c.text)}?<fail={vMessageError}> ;

relOp							: EQ | NEQ | GT | LT | GEQT | LEQT ;
mathOp							: ADD | SUB | MUL | DIV ;
boolOp							: isEmptyDefinition | notEmptyDefinition | OR | AND ;
nowDefinition					: GNOW BRACKETSTART BRACKETSTOP;
averageDefinition				: GAVERAGE BRACKETSTART BRACKETSTOP;
averageDefinitionParam			: GAVERAGE BRACKETSTART paramAttributeName BRACKETSTOP;
sumDefinition					: GSUM BRACKETSTART BRACKETSTOP ;
sumDefinitionParam				: GSUM BRACKETSTART paramAttributeName BRACKETSTOP ;
sizeDefinition					: GSIZE BRACKETSTART BRACKETSTOP ;
sizeDefinitionParam				: GSIZE BRACKETSTART paramAttributeName BRACKETSTOP ;
isEmptyDefinition				: BEMPTY BRACKETSTART BRACKETSTOP ;
notEmptyDefinition				: BNOTEMPTY BRACKETSTART BRACKETSTOP ;
inclDefinition					: INCLUDING BRACKETSTART oclExpression BRACKETSTOP ;


/* LEXICAL RULES */
INIT			: 'init';
DERIVE			: 'derive';
INV				: 'inv';
BOOLTYPE		: 'boolean';
DATETYPE		: 'duration' |'dateTime' | 'date' | 'time' | 'gYearMonth' | 'gYear' | 'gMonthDay' | 'gDay' | 'gMonth';
NUMBERTYPE		: DECIMALTYPE | INTEGERTYPE | MONEYTYPE ;
DECIMALTYPE		: DECIMAL | 'nonPositiveDecimal' | 'negativeDecimal' | 'nonNegativeDecimal' | 'positiveDecimal';
INTEGERTYPE		: 'integer' | 'nonPositiveInteger' | 'negativeInteger' | 'nonNegativeInteger' | 'positiveInteger';
MONEYTYPE		: 'money' | 'nonPositiveMoney' | 'negativeMoney' | 'nonNegativeMoney' | 'positiveMoney' ;
DECIMAL			: 'decimal';
COMMENT			: '--' .*? NEWLINE -> skip ;
BOOLEAN			: 'True' | 'False' ;
CALL			: '->' ;
GNOW			: 'now';
GSUM			: 'sum' ;
GAVERAGE		: 'average';
GSIZE			: 'size' ;
BEMPTY			: 'isEmpty' ;
BNOTEMPTY		: 'notEmpty' ;
COLLECT			: 'collect' ;
ITERATE			: 'iterate' ;
INCLUDING		: 'including' ;
FLOAT			: 'float' ;
TSET			: 'Set' ;
NULL			: 'null';
BRACKETSTART	: '(' ;
BRACKETSTOP		: ')' ;
BRACESTART		: '{' ;
BRACESTOP		: '}' ;
EQ				: '=' ;
NEQ				: '!=' ;
NOT				: '!' ;  /* PAS */
GT				: '>' ;
LT				: '<' ;
GEQT			: '>=' ;
LEQT			: '<=' ;
ADD				: '+' ;
SUB				: '-' ;
MUL				: '*' ;
DIV				: '/' ;
OR				: 'or' ; /* PAS */
AND				: 'and' ; /* PAS */
INT				: '-'?DIGIT+ ;
DEC				: '-'?DIGIT+('\''DIGIT+)*'.' DIGIT+;
DURATION		: '-'?'P'(DIGIT+'Y')?(DIGIT+'M')?(DIGIT+'D')?('T' (DIGIT+'H')?(DIGIT+'M')?(DIGIT+'S')? )? ;
DATETIME		: DATE SPACE TIME;
DATE			: DIGIT+'.'DIGIT+'.'DIGIT+;
TIME			: DIGIT+(':'DIGIT+(':'DIGIT+)?)?;
GYEARMONTH		: GMONTH SPACE DIGIT+;
GMONTHDAY		: DIGIT+ SPACE GMONTH;
CURRENCYCODE	: [A-Z]+;
WORD			: [a-zA-Z] [_0-9a-zA-Z]* ;
GMONTH			: [a-z]+;  /* PAS 2022-03-24 - voir le formatage pour ajouter éû  [a-zéû]+ */
WS				: (SPACE | NEWLINE)+ -> skip ;
SPACE			: [ \t] ; 			/* Space or tab */
NEWLINE			: '\r'? '\n' ;		/* Carriage return and new line */

fragment
DIGIT			: [0-9];

