
/*
 * This is a class for response objects, 
 * instantiated by RequestHandler
 */

public class Response {
	public final int HTTP_STATUS;
	public final String FILETYPE;
	public final int FILESIZE;
	public byte[] FILE_CONTENTS;


	public Response(int HTTP_STATUS, int FILETYPE, int FILESIZE, byte[] FILE_CONTENTS) {
		this.HTTP_STATUS = HTTP_STATUS;
		this.FILETYPE = FILETYPE;
		this.FILESIZE = FILESIZE;
		this.FILE_CONTENTS = FILE_CONTENTS;
	} // constructor
} // class
 
