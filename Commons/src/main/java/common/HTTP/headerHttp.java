package common.HTTP;

import common.interaction.User;

public class headerHttp {
	private User user;
	private String host;
	private String contentType;
	private int contentLength;
	private String connection;
	
	public headerHttp(User user, String host, String contentType, int contentLength, String connection) {
		this.user = user;
		this.contentLength = contentLength;
		this.contentType = contentType ;
		this.host = host;
		this.connection = connection;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	@Override
	   public String toString() {
	       String info = "";
	       info += "User: " + user.getUsername() + "\r\n";
	       info += "Host: " + host + "\r\n";
	       info += "Content-Type: " + contentType + "\r\n";
	       info += "Content-Length: " + contentLength + "\r\n";
	       info += "Connection: " + connection;
	       return info;
	} 
}
