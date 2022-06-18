package common.HTTP;

import common.interaction.response;

public class bodyResponseHttp {
	private response responseFromServer;

	public bodyResponseHttp(response responseFromServer) {
		super();
		this.responseFromServer = responseFromServer;
	}

	public response getResponseFromServer() {
		return responseFromServer;
	}

	public void setResponseFromServer(response responseFromServer) {
		this.responseFromServer = responseFromServer;
	}
	@Override
	   public String toString() {
	       String info = "";
	       if(responseFromServer.getResponseBody() != null) {
	       info += responseFromServer.getResponseBody() + "\r\n"; }      
	       return info;
	} 

}
