package generatorsql.generator;

public enum MPDRGenerateSQLIntegrityError {
	DATATYPE_BOOLEAN("mpdr.constraint.mess.err.datatype.boolean", "",1),
	DATATYPE_NORMALIZEDSTRING("mpdr.constraint.mess.err.datatype.normalizedstring", "",2),
	DATATYPE_TOKEN("mpdr.constraint.mess.err.datatype.token", "", 3),
	DATATYPE_WORD("mpdr.constraint.mess.err.datatype.word", "",4),
	DATATYPE_INTEGER("mpdr.constraint.mess.err.datatype.integer", "",5),
	DATATYPE_NUMBER_POSITIVE("mpdr.constraint.mess.err.datatype.number.positive", "", 6),
	DATATYPE_NUMBER_NONNEGATIVE("mpdr.constraint.mess.err.datatype.number.nonnegative", "", 7),
	DATATYPE_NUMBER_NEGATIVE("mpdr.constraint.mess.err.datatype.number.negative", "", 8),
	DATATYPE_NUMBER_NONPOSITIVE("mpdr.constraint.mess.err.datatype.number.nonpositive", "", 9),
	COLUMN_FROZEN("mpdr.constraint.mess.err.column.frozen", "", 10),
	FK_FROZEN("mpdr.constraint.mess.err.fk.frozen", "", 11),
	COLUMN_MANDATORY_PSEUDO_ENTITY("mpdr.constraint.mess.err.column.mandatory.pseudo.entity", "", 12) ,
	COLUMN_WIHOUTLINK_PSEUDO_ENTITY("mpdr.constraint.mess.err.column.withoutlink.pseudo.entity", "", 13),
	ASSNNNNONORIENTED_NO_LINK_INVERSE("mpdr.constraint.mess_err.assnnnonoriented.no.link.inverse", "", 14),
	DATATYPE_INTEGER_PRECISION("mpdr.constraint.mess.err.datatype.integer.precision", "",15);

	private String messErrProperty;
	private String help;
	private Integer noErr;

	private MPDRGenerateSQLIntegrityError(String messageErr, String help, Integer noErr){
		this.messErrProperty = messageErr;
		this.help = help;
		this.noErr = noErr;
}
	
	public String getHelp() {
		return this.help;
	}
	
	public String getMessErrProperty() {
		return this.messErrProperty;
	}

	/*
	public String getMessErrText(String[] params) {
		return MessagesBuilder.getMessagesProperty(this.messErrProperty, params);
	}

	 */

	public Integer getNoErr() {
		return noErr;
	}
	
}
