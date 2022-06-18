package common.interaction;

public enum responseCode {

	CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
	CLIENT_ERROR_401_METHOD_NOT_AUTHORIZED(401, "Not Authorized"),
	
	
	SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),
	SERVER_ERROR_404_NOT_FOUND(404, "Not Found"),
	SERVER_ERROR_403_FORBIDDEN(403, "Forbidden"),
	SERVER_EXIT(0,null),
	
	OK_200_OK(200, "Ok"),
	
	ACCEPTED_201_ACCEPTED(201, "Accepted");

	
	public final int STATUSCODE;
	public final String MESSAGE;
	
	responseCode(int STATUSCODE, String MESSAGE) {
		this.STATUSCODE = STATUSCODE;
		this.MESSAGE = MESSAGE;
	    
}
}
