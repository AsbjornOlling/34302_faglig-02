
/*
 * This is a class for response objects, 
 * instantiated by RequestHandler
 */

import java.util.*;
import java.text.SimpleDateFormat;

public class Response {
	public String DATE_CREATED;
	public final int HTTP_STATUS;
	public final String FILETYPE;
	public final int FILESIZE;
	public byte[] FILE_CONTENTS; // can this be final?


	public Response(int HTTP_STATUS, String FILETYPE, int FILESIZE, byte[] FILE_CONTENTS) {
		this.DATE_CREATED = date();
		this.HTTP_STATUS = HTTP_STATUS;
		this.FILETYPE = FILETYPE;
		this.FILESIZE = FILESIZE;
		this.FILE_CONTENTS = FILE_CONTENTS;
	} // constructor

	private String date() {
		Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
	}

} // class
 
