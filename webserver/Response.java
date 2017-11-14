
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
	public byte[] FILE_CONTENTS; // can this be final?


	public Response(int HTTP_STATUS, String FILETYPE, byte[] FILE_CONTENTS) {
		this.HTTP_STATUS = "HTTP/1.0 "+HTTP_STATUS+" OK"+"\r\n";
		this.HTTP_DATE = date();

		// Generate http "Content-Type:" line
		// check it agaisnt a list of recognized files
		ArrayList<String> imageTypes = new ArrayList<String>();
		imageTypes.add("gif");
		imageTypes.add("png");
		imageTypes.add("bmp");
		imageTypes.add("jpg");
		String httpTypeLine = "Content-Type: ";
		if ( imageTypes.contains(FILETYPE) ) {
			httpTypeLine += "image/"+FILETYPE;
		} else if ( FILETYPE == "html") {
			httpTypeLine += "image/html";
		} else {
			throw new IllegalArgumentException("ERROR: Unknown filetype requested");
		}
		httpTypeLine += "\r\n";
		this.HTTP_CONTENTTYPE = httpTypeLine;

		// figure out "Content-Length" line
		this.HTTP_CONTENTLENGTH = "Content-Length: " + FILE_CONTENTS.length + "\r\n";

		this.FILE_CONTENTS = FILE_CONTENTS;
	} // constructor

	// obs: this isn't tested, but should work
	private String date() {
		Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
	}

} // class
 
