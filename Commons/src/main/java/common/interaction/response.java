package common.interaction;

public class response  {
    private responseCode responseCode;
    private String responseBody;

    public response(responseCode responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public response() {
		// TODO Auto-generated constructor stub
	}

	/**
     * @return Response Ñ�ode.
     */
    public responseCode getResponseCode() {
        return responseCode;
    }

    /**
     * @return Response body.
     */
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Response[" + responseCode + ", " + responseBody + "]";
    }
}
