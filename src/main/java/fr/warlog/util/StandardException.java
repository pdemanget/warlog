package fr.warlog.util;

/**
 * Application Exception
 * @author philippe.demanget
 *
 */
@SuppressWarnings("serial")
public class StandardException extends RuntimeException{
	
	public StandardException(String msg, Throwable t){
		super(msg,t);
	}
	
}
