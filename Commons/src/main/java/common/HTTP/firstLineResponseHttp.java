package common.HTTP;

import common.interaction.responseCode;

public class firstLineResponseHttp {
    private final String HTTP = "HTTP/1.1";
    private responseCode status;
    public firstLineResponseHttp(responseCode status ) {
    	this.status = status;
    }
	public responseCode getStatus() {
		return status;
	}
	public void setStatus(responseCode status) {
		this.status = status;
	}
	@Override
	   public String toString() {
	       String info = "";
	       info += HTTP;
	       info += " " + status.STATUSCODE;
	       info += " " + status.MESSAGE;
	       return info;
	} 


}
