package net.plaidypus.deadreckoning.exceptions;

@SuppressWarnings("serial")
public class ModLoadException extends Exception {
	
	ErrorType type;
	String error = "";
	
	public ModLoadException()
	{
		super();
		type = ErrorType.UNKNOWN;
		
	}
	
	public ModLoadException(ErrorType t)
	{
		super();
		type = t;
	}
	
	public ModLoadException(String e)
	{
		super(e);
		error = e;
		type = ErrorType.UNKNOWN;
	}
	
	public ModLoadException(String e, ErrorType t)
	{
		super(e);
		error = e;
		type = t;
	}
	
	public ErrorType getType()
	{
		return type;
	}

}
