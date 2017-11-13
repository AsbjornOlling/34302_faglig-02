/*
 * Parses the text gotten from ClientConnection.java,
 * fetches a file from the content directory,
 * and returns (some kind of) outputtable object to ClientConnection.
 * 
 */

  //Fields
  String status;

 public handler (String URI) {
	 String[] split = URI.split("\\s+");
	 System.out.println(split[1]);
 }
