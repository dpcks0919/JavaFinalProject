package edu.handong.csee;

public class MissingDataException extends Exception {

	public MissingDataException() {
		super("edu.handong.csee.MissingDataException occured");
	}

	public MissingDataException(String message) {
		super(message);
	}

}
