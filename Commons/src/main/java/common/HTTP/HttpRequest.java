package common.HTTP;

public class HttpRequest  {
	private firstLineHttp firstLine;
	private headerHttp header;
	private bodyHttp body;
	
	public HttpRequest() {
		super();
	}
	public HttpRequest(firstLineHttp firstLine, headerHttp header) {
		this.firstLine = firstLine;
		this.header = header;
	}
	
	public HttpRequest(firstLineHttp firstLine, headerHttp header, bodyHttp body) {
		this.body = body;
		this.firstLine = firstLine;
		this.header = header;
	}
	public bodyHttp Getbody() {
		return body;
	}
	public firstLineHttp GetFirstLine() {
		return firstLine;
	}
	@Override
	   public String toString() {
	       String info = "";
	       info += firstLine.toString() + "\r\n";
	       info += header.toString() + "\r\n";
	       info += body.toString();
	       return info;
	} 

}
