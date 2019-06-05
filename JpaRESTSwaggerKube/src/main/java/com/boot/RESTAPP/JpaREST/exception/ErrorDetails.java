package com.boot.RESTAPP.JpaREST.exception;

import java.util.Date;

public class ErrorDetails {

	/*This class provide custom fields which will be shown in the time of errors in HTTP response
	 * This is specific error response structure.
	 * To use this class we will create another class which will handle specific as well as global exceptions.
	*/
	
	
	private Date timeStamp;
	private String message;
	private String details;
	
	public ErrorDetails(Date timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}
	
	public Date getTimestamp() {
		return timeStamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDetails() {
		return details;
	}
}
