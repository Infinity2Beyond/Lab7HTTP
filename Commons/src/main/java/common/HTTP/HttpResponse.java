package common.HTTP;

public class HttpResponse  {
	private bodyResponseHttp body;
	private headerResponseHttp header;
	private firstLineResponseHttp firstLine;
	public HttpResponse() {
		super();
	}
	public HttpResponse(firstLineResponseHttp firstLine, headerResponseHttp header)  {
		this.firstLine = firstLine;
		this.header = header;
	}
	
	public HttpResponse(firstLineResponseHttp firstLine, headerResponseHttp header, bodyResponseHttp body)  {
		this.body = body;
		this.firstLine = firstLine;
		this.header = header;
	}
	public bodyResponseHttp getBody() {
		return body;
	}
	@Override
	   public String toString() {
	       String info = "";
	       info += firstLine.toString() + "\r\n";
	       info += header.toString() + "\r\n" +"\r\n";
	       info += body.toString();
	       return info;
	} 

}
