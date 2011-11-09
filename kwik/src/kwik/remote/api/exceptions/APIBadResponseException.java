package kwik.remote.api.exceptions;

import kwik.remote.api.Error;

public class APIBadResponseException extends Exception {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public Error error;
	
	public APIBadResponseException(Error error) {
		this.error = error;
	}
}
