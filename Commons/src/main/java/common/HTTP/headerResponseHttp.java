package common.HTTP;

import java.time.LocalDateTime;

public class headerResponseHttp {
		
	private String server;
	private LocalDateTime date;
	private String contentType;
	private int contentLength;
	private String connection;
	public headerResponseHttp(String server, LocalDateTime date, String contentType, int contentLength,
			String connection) {
		super();
		this.server = server;
		this.date = date;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.connection = connection;
	}
	@Override
	   public String toString() {
	       String info = "";
	       info += "Server: " + server + "\r\n";
	       info += "Date: " + date + "\r\n";
	       info += "Content-Type: " + contentType + "\r\n";
	       info += "Content-Length: " + contentLength + "\r\n";
	       info += "Connection: " + connection;
	       return info;
	} 
}
