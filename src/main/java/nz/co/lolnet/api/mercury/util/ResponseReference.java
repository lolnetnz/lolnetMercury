package nz.co.lolnet.api.mercury.util;

import nz.co.lolnet.api.mercury.Mercury;

public class ResponseReference {
	
	/* Json Response Messages */
	public static final String INFO = "{\"Name\":\"Mercury\",\"Version\":\"" + Mercury.version + "\",\"Author\":\"lolnetnz\"}";
	
	public static final String ACCEPTED = "{\"data\":{\"code\":202,\"message\":\"Accepted\"}}";
	
	public static final String BAD_REQUEST = "{\"error\":{\"code\":400,\"message\":\"Bad Request\"}}";
	public static final String FORBIDDEN = "{\"error\":{\"code\":403,\"message\":\"Forbidden\"}}";
	public static final String NOT_FOUND = "{\"error\":{\"code\":404,\"message\":\"Not Found\"}}";
	public static final String METHOD_NOT_ALLOWED = "{\"error\":{\"code\":405,\"message\":\"Method Not Allowed\"}}";
	public static final String NOT_ACCEPTABLE = "{\"error\":{\"code\":406,\"message\":\"Not Acceptable\"}}";
	
	public static final String INTERNAL_SERVER_ERROR = "{\"error\":{\"code\":500,\"message\":\"Internal Server Error\"}}";
	
	/* ContentTypes */
	public static final String APPLICATION_JSON = "application/json";
}