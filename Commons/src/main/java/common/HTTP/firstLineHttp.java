package common.HTTP;

import common.interaction.*;

public class firstLineHttp {
	private processingCode method;
	private request request;
    private final String HTTP = "HTTP/1.1";
    
    public firstLineHttp (processingCode method, request request) {
    	this.method = method;
    	this.request = request;
    }

	public processingCode getMethod() {
		return method;
	}

	public void setMethod(processingCode method) {
		this.method = method;
	}

	public request getRequest() {
		return request;
	}

	public void setRequest(request request) {
		this.request = request;
	}
	@Override
	   public String toString() {
	       String info = "";
	       info += method.name();
	       info += " /";
	       info += request.getCommandName();
	       info += "/ ";
	       info += HTTP;
	       return info;
	} 
}
