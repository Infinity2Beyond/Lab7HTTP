package common.HTTP;

import common.interaction.*;

public class bodyHttp {
	private request request;
	public bodyHttp(request request) {
		this.request = request;
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
	       if(request.getCommandStringArgument() != null) {
	       info += request.getCommandStringArgument() + "\r\n"; }
	       if(request.getCommandObjectArgument() != null) {
		   info += request.getCommandObjectArgument().toString() + "\r\n"; }
	       if(request.getUser() != null) {
			   info += request.getUser().toString() + "\r\n"; }
	       return info;
	} 
}
