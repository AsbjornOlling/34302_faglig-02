
/*
 * This is a class for response objects, 
 * instantiated by RequestHandler
 */

import java.util.*;
import java.text.SimpleDateFormat;

public class Response {
	public final String HTTP_STATUS;
	public final String HTTP_DATE;
	public final String HTTP_CONTENTTYPE;
	public final String HTTP_CONTENTLENGTH; // in bytes
	public final byte[] FILE_CONTENTS; // can this be final?


	public Response(int httpStatus, String filetype, byte[] fileContents) {
		this.HTTP_STATUS = "HTTP/1.0 "+httpStatus+" OK"+"\r\n";
		this.HTTP_DATE = date();
		this.FILE_CONTENTS = fileContents;

		// figure out "Content-Length" line
		this.HTTP_CONTENTLENGTH = "Content-Length: " + fileContents.length + "\r\n";

		// the rest of the constructer is HTTP Content-Type
		// make whitelist of accepted filetypes
		ArrayList<String> imageTypes = new ArrayList<String>();
		imageTypes.add("gif");
		imageTypes.add("png");
		imageTypes.add("bmp");
		imageTypes.add("jpg");

		ArrayList<String> audioTypes = new ArrayList<String>();
		audioTypes.add("mp3");
		audioTypes.add("ogg");

		ArrayList<String> textTypes = new ArrayList<String>();
		textTypes.add("html");
		textTypes.add("css");
		textTypes.add("javascript");

		// list js as javascript
		if ( filetype.equals("js") ) {
			filetype = "javascript";
		}

		// Generate http "Content-Type:" line
		String httpTypeLine = "Content-Type: ";
		if ( imageTypes.contains(filetype) ) {
			httpTypeLine += "image/";
		} else if ( audioTypes.contains(filetype) ) {
			httpTypeLine += "audio/";
		} else if ( textTypes.contains(filetype) ) {
			httpTypeLine += "text/";
		} else {
			throw new IllegalArgumentException("ERROR: Unknown filetype requested: "+filetype);
		}
		httpTypeLine += filetype+"\r\n";
		this.HTTP_CONTENTTYPE = httpTypeLine;
	} // constructor


	// gets current GMT time
	// TODO consider if better to put straight into constructor
	private String date() {
		Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime())+"\r\n";
	}

} // class
 
