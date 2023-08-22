package panaderia.utilidades;
import java.util.Hashtable;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.io.File;

import panaderia.utilidades.EmptyInputStream;
import panaderia.utilidades.TempFile;

public class MultipartRequest
{
    /**
        Define Character Encoding method here.
    */
    private String charEncoding;
    public static final String DEF_ENCODING = "ISO-8859-1";

    // If not null, send debugging out here.
    private PrintWriter debug = null;

    private Hashtable htParameters = null;
    private Hashtable htFiles = null;

    private String strBoundary = null;
    
    // If this Directory spec remains null, writing of files will be disabled...
    private File fileOutPutDirectory = null;
    private boolean loadIntoMemory = false;

	private long intMaxContentLength;
    private long intContentLength;
	private int maxBytesExceededMode;

	/**
	 * This stores the actual total read including all multipart boundaries, etc.
	 */
    private long intTotalRead;

    /**
        Prevent a denial of service by defining this, will never read more data.
        If Content-Length is specified to be more than this, will throw an exception.

        This limits the maximum number of bytes to the value of an int, which is 2 Gigabytes.
    */
    public static final int MAX_READ_BYTES = 15 * (1024 * 1024); // 2MB!

    /**
        Defines the number of bytes to read per readLine call. 128K
    */
    public static final int READ_LINE_BLOCK = 1024 * 128;

    /**
        Store a read from the input stream here.  Global so we do not keep creating new arrays each read.
    */
    private byte[] blockOfBytes = null;

    /**
        Define the array indexes for the htFiles Object array.
    */
    public static final int FILENAME = 0;
    public static final int CONTENT_TYPE = 1;
    public static final int SIZE = 2;
    
    // Only used for file upload to memory.
    public static final int CONTENTS = 3;

    // Only used for file uploaded to file system. This is the temporary unique filename of the saved file
    public static final int TMP_FILENAME = 4;

    // The raw filename string as sent by the browser.
    public static final int RAW_FILENAME = 5;

	/**
	 * Mode
	 */
	public static final int ABORT_IF_MAX_BYES_EXCEEDED = 100;
	public static final int IGNORE_FILES_IF_MAX_BYES_EXCEEDED = 101;
	
    /**
        Sets up the encoding for this instance of multipartrequest. You can set the encoding 
        to null, in which case the default encoding will be applied.  The default encoding if 
        this method is not called has been set to ISO-8859-1, which seems to offer the best hope
        of support for international characters, such as german "Umlaut" characters.
    */
    public synchronized void setEncoding(String enc)throws UnsupportedEncodingException
    {
        if (enc==null || enc.trim()=="")
            charEncoding = System.getProperty("file.encoding");
        else
        {
            // This will test the encoding for validity.
            new String(new byte[]{(byte)'\n'}, enc);

            charEncoding = enc;
        }
    }

    /**
        Returns the current encoding method.
    */
    public String getEncoding()
    {
        return charEncoding;
    }

     /** 
     * Standard Constructor
     *
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param strSaveDirectory      The temporary directory to save the file from where they can then be moved to wherever by the
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     *
     * @see #MAX_READ_BYTES
     * @deprecated Replaced by MultipartRequest(PrintWriter, String, int, InputStream, String, int, boolean, String)  
     */
    public MultipartRequest(String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            String strSaveDirectory) throws IllegalArgumentException, IOException
    {
		initParser(null, // debug 
			    strContentTypeText, 
				intContentLength, 
				in, 
				false, //loadIntoMemory
				strSaveDirectory,
				MultipartRequest.MAX_READ_BYTES, 
				MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
				null); //encoding
    }
    
    /** 
     * Standard Constructor
     *
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param strSaveDirectory      The temporary directory to save the file from where they can then be moved to wherever by the
     *                              calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
     *                              will be silently ignored.</B>
	 * @param intMaxReadBytes       Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     *
     * @see #MAX_READ_BYTES
     * @deprecated Replaced by MultipartRequest(PrintWriter, String, int, InputStream, String, int, boolean, String)  
     */
    public MultipartRequest(String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            String strSaveDirectory, 
                            int intMaxReadBytes) throws IllegalArgumentException, IOException
    {
		initParser(null, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				false, //loadIntoMemory
				strSaveDirectory,
				intMaxReadBytes, 
				MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
				null); //encoding
    }

    /** 
     * Standard Constructor
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param strSaveDirectory      The temporary directory to save the file from where they can then be moved to wherever by the
     *                              calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
     *                              will be silently ignored.</B>
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     *
     * @see #MAX_READ_BYTES
     * @deprecated Replaced by MultipartRequest(PrintWriter, String, int, InputStream, String, int, boolean, String)  
     */
    public MultipartRequest(PrintWriter debug, 
                            String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            String strSaveDirectory) throws IllegalArgumentException, IOException
    {
		initParser(debug, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				false, //loadIntoMemory
				strSaveDirectory,
				MultipartRequest.MAX_READ_BYTES, 
				MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
				null); //encoding
    }

    /** 
     * Memory Constructor
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
	 * @param intMaxReadBytes       Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     *
     * @see #MAX_READ_BYTES
     * @deprecated Replaced by MultipartRequest(PrintWriter, String, int, InputStream, int, boolean, String)  
     */
    public MultipartRequest(PrintWriter debug, 
                            String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            int intMaxReadBytes) throws IllegalArgumentException, IOException
    {
		initParser(debug, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				true, //loadIntoMemory
				null, //strSaveDirectory
				intMaxReadBytes, 
				MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
				null); //encoding
    }
	
	/** 
     * Standard Constructor
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param strSaveDirectory      The temporary directory to save the file from where they can then be moved to wherever by the
     *                              calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
     *                              will be silently ignored.</B>
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     *
     * @see #MAX_READ_BYTES
     * @deprecated Replaced by MultipartRequest(PrintWriter, String, int, InputStream, String, int, boolean, String)  
     */
	public MultipartRequest(PrintWriter debug, 
                            String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            String strSaveDirectory, 
                            int intMaxReadBytes) throws IllegalArgumentException, IOException, UnsupportedEncodingException
    {
		initParser(debug, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				false, //loadIntoMemory
				strSaveDirectory,
				intMaxReadBytes, 
				MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
				null);//encoding
    }

    /** 
     * Standard Constructor
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param strSaveDirectory      The temporary directory to save the file from where they can then be moved to wherever by the
     *                              calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
     *                              will be silently ignored.</B>
     * @param intMaxReadBytes       Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
     * @param encoding              Sets the encoding to use. If null, ISO-8859-1 will be used.
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     * @exception UnsupportedEncodingException If the encoding is invalid.
     *
     * @see #MAX_READ_BYTES
     */
    public MultipartRequest(PrintWriter debug, 
                            String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            String strSaveDirectory, 
                            int intMaxReadBytes,
                            String encoding) throws IllegalArgumentException, IOException, UnsupportedEncodingException
    {
		initParser(debug, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				false, //loadIntoMemory
				strSaveDirectory,
				intMaxReadBytes,
				MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
				encoding);
    }
	
	/** 
     * Memory Constructor
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param intMaxReadBytes       Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
     * @param encoding              Sets the encoding to use. If null, ISO-8859-1 will be used.
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     * @exception UnsupportedEncodingException If the encoding is invalid.
     *
     * @see #MAX_READ_BYTES
     */
    public MultipartRequest(PrintWriter debug, 
                            String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            int intMaxReadBytes,
                            String encoding) throws IllegalArgumentException, IOException, UnsupportedEncodingException
    {
		initParser(debug, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				true, //loadIntoMemory
				null, // strSaveDirectory
				intMaxReadBytes, 
				MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
				encoding);
    }

	/** 
     * Standard Constructor
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param strSaveDirectory      The temporary directory to save the file from where they can then be moved to wherever by the
     *                              calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
     *                              will be silently ignored.</B>
     * @param intMaxReadBytes       Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
	 * @param maxBytesExceededMode	This controls how the parser will process a request which is in excess of the intMaxReadBytes
	 * 								parameter.  The possible modes are:
	 *                              <ul>
	 *                              <li>MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED - The parser will throw a MaxBytesReadException</li>
	 *                              <li>MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED - All parameters will be processed, but any file
	 *                              content will be discarded.  <b><u>Be warned that there is still potential for Denial-of-Service, if an attacker decided to send
	 *                              megabytes of non-file form data.</u></b></li>
	 * 								<ul>
     * @param encoding              Sets the encoding to use. If null, ISO-8859-1 will be used.
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     * @exception UnsupportedEncodingException If the encoding is invalid.
     *
     * @see #MAX_READ_BYTES
     */
    public MultipartRequest(PrintWriter debug, 
                            String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            String strSaveDirectory, 
                            int intMaxReadBytes,
							int maxBytesExceededMode,
                            String encoding) throws IllegalArgumentException, IOException, UnsupportedEncodingException
    {
		initParser(debug, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				false, //loadIntoMemory
				strSaveDirectory,
				intMaxReadBytes,
				maxBytesExceededMode,
				encoding);
    }
	
	/** 
     * Memory Constructor
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
     * @param intMaxReadBytes       Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
	 * @param maxBytesExceededMode	This controls how the parser will process a request which is in excess of the intMaxReadBytes
	 * 								parameter.  The possible modes are:
	 *                              <ul>
	 *                              <li>MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED - The parser will throw a MaxBytesReadException</li>
	 *                              <li>MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED - All parameters will be processed, but any file
	 *                              content will be discarded.  <b><u>WARNING: There is still potential for a Denial-of-Service.  For instance, an attacker can send
	 *                              many megabytes of non-file form data.</u></b></li>
	 * 								<ul>
     * @param encoding              Sets the encoding to use. If null, ISO-8859-1 will be used.
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     * @exception UnsupportedEncodingException If the encoding is invalid.
     *
     * @see #MAX_READ_BYTES
     */
    public MultipartRequest(PrintWriter debug, 
                            String strContentTypeText, 
                            int intContentLength, 
                            InputStream in, 
                            int intMaxReadBytes,
							int maxBytesExceededMode,
                            String encoding) throws IllegalArgumentException, IOException, UnsupportedEncodingException
    {
		initParser(debug, 
			    strContentTypeText, 
				intContentLength, 
				in, 
				true, //loadIntoMemory
				null, // strSaveDirectory
				intMaxReadBytes, 
				maxBytesExceededMode,
				encoding);
    }

    /** 
     * Initialise the parser.
     *
     * @param debug                 A PrintWriter that can be used for debugging.
     * @param strContentTypeText    The &quot;Content-Type&quot; HTTP header value.
     * @param intContentLength      The &quot;Content-Length&quot; HTTP header value.
     * @param in                    The InputStream to read and parse.
	 * @param loadIntoMemory        Is this parser loading its output into memory only.
     * @param strSaveDirectory      The temporary directory to save the file from where they can then be moved to wherever by the
     *                              calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
     *                              will be silently ignored.</B>
     * @param intMaxReadBytes       Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
	 * @param maxBytesExceededMode	This controls how the parser will process a request which is in excess of the intMaxReadBytes
	 * 								parameter.  The possible modes are:
	 *                              <ul>
	 *                              <li>MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED - The parser will throw a MaxBytesReadException</li>
	 *                              <li>MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED - All parameters will be processed, but any file
	 *                              content will be discarded.  <b><u>WARNING: There is still potential for a Denial-of-Service.  For instance, an attacker can send
	 *                              many megabytes of non-file form data.</u></b></li>
	 * 								<ul>
     * @param encoding              Sets the encoding to use. If null, ISO-8859-1 will be used.
     *
     * @exception IllegalArgumentException  If the strContentTypeText does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
     * @exception IOException               If the intContentLength is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
     * @exception UnsupportedEncodingException If the encoding is invalid.
     *
     * @see #MAX_READ_BYTES
     */
    private void initParser(PrintWriter debug, 
                        String strContentTypeText, 
                        int intContentLength, 
                        InputStream in,
						boolean loadIntoMemory,
						String strSaveDirectory,
						int intMaxReadBytes,
						int maxBytesExceededMode,
                        String encoding) throws IllegalArgumentException, IOException, UnsupportedEncodingException
    {
        // save reference to debug stream for later.
        this.debug = debug;

		// If true - ignore the directory specification.		
		this.loadIntoMemory = loadIntoMemory;
        
		// IF strSaveDirectory == NULL, then we should ignore any files uploaded.
        if (!loadIntoMemory && strSaveDirectory!=null)
        {
            this.fileOutPutDirectory = new File(strSaveDirectory);
            if (!fileOutPutDirectory.exists())
                throw new IOException("Directory ["+strSaveDirectory+"] is invalid.");
            else if (!fileOutPutDirectory.canWrite())
                throw new IOException("Directory ["+strSaveDirectory+"] is readonly.");
		}
		
		// Set the encoding.
        setEncoding( (encoding!=null) ? encoding : DEF_ENCODING );

        if (strContentTypeText!=null && strContentTypeText.startsWith("multipart/form-data") && strContentTypeText.indexOf("boundary=")!=-1)
		{
            strBoundary = strContentTypeText.substring(strContentTypeText.indexOf("boundary=")+"boundary=".length()).trim();
		}
        else
        {
            // <mtl,jpell>
            debug("ContentType = " + strContentTypeText);
            throw new IllegalArgumentException("Invalid Content Type.");
        }

        this.intContentLength = intContentLength;
		this.intMaxContentLength = intMaxReadBytes;
		this.maxBytesExceededMode = maxBytesExceededMode;

        if(maxBytesExceededMode == MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED && this.intContentLength > this.intMaxContentLength)// FIX: 1.15
        {
            debug("ContentLength = " + this.intContentLength);
            debug("MaxReadBytes = " + this.intMaxContentLength);
            
            throw new IllegalArgumentException("Maximo");
        }

        // Instantiate the hashtable...
        htParameters = new Hashtable();
        htFiles = new Hashtable();
        blockOfBytes = new byte[READ_LINE_BLOCK];

        // Even though this method would never normally be called more than once
        // in the objects lifetime, we will initialise it here anyway.
        intTotalRead=0; // <David Tuma> - fix: 1.20

		// Now parse the data.
		parse(new BufferedInputStream(in));

        // No need for these once parse is complete.
        this.blockOfBytes=null;
        this.debug = null;
        this.strBoundary=null;
    }

	/**
	 * If this class was constructed with a maxBytesExceeded mode of 
	 * MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED, this method
	 * will indicate whether the process is ignoring file content because
	 * the content-length was exceeded.
	 */
	public boolean isMaxBytesExceeded()
	{
		return (this.intContentLength > this.intMaxContentLength);
	}
	
    /**
        Return the value of the strName URLParameter.
        If more than one value for a particular Parameter, will return the first.
        If an error occurs will return null.
    */
    public String getURLParameter(String strName)
    {                                        
        Object value = htParameters.get(strName);
        if (value instanceof Vector)
            return (String) ((Vector)value).firstElement();
        else
            return (String) htParameters.get(strName);
    }

    /**
        Return an enumeration of all values for the strName parameter.
        Even if a single value for, will always return an enumeration, although
        it may actually be empty if not value was encountered for strName or
        it is an invalid parameter name.
    */
    public Enumeration getURLParameters(String strName)
    {
        Object value = htParameters.get(strName);
        if (value instanceof Vector)
            return ((Vector)value).elements();
        else
        {
            Vector vector = new Vector();
            if(value!=null)
                vector.addElement(value);
            return vector.elements();
        }
    }

    /**
        An enumeration of all URL Parameters for the current HTTP Request.
    */
    public Enumeration getParameterNames()
    {
        return htParameters.keys();
    }

    /**
        This enumeration will return all INPUT TYPE=FILE parameter NAMES as encountered
        during the upload.
    */
    public Enumeration getFileParameterNames()
    {
        return htFiles.keys();
    }

    /**
        Returns the Content-Type of a file.

        @see #getFileParameter(java.lang.String, int)
    */
    public String getContentType(String strName)
    {
        // Can cast null, it will be ignored.
        return (String)getFileParameter(strName, CONTENT_TYPE);
    }
    
    /**
        If files were uploaded into memory, this method will retrieve the contents
        of the file as a InputStream.  

        @return the contents of the file as a InputStream, or null if not file uploaded,
        or file uploaded to file system directory.

        @see #getFileParameter(java.lang.String, int)
    */
    public InputStream getFileContents(String strName)
    {
		if(this.maxBytesExceededMode == MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED && 
				this.intContentLength >= this.intMaxContentLength) // file not available.
		{
			return null;
		}
		else
		{
			Object obj = getFileParameter(strName, CONTENTS);
			if (obj!=null)
			    return new ByteArrayInputStream((byte[])obj);
			else if(getBaseFilename(strName)!=null)
				return new EmptyInputStream(); // empty file, but lets return an inputstream anyway.
			else
				return null;
		}
    }

    /**
        Returns a File reference to the uploaded file.  This reference is to the files uploaded location,
        and allows you to read/move/delete the file.

        This method is only of use, if files were uploaded to the file system.  Will return null if 
        uploaded to memory, in which case you should use getFileContents(strName) instead.

        @return Returns a null file reference if a call to getFileSize(strName) returns zero or files were
        uploaded to memory.

        @see #getFileSize(java.lang.String)
        @see #getFileContents(java.lang.String)
    */
    public File getFile(String strName)
    {
		if(this.maxBytesExceededMode == MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED && 
				this.intContentLength >= this.intMaxContentLength) // file not available.
		{
			return null;
		}
		else
		{
			// Fix: If fileOutPutDirectory is null, then we are ignoring any file contents, so we must return null.
			if(getFileSize(strName)>0 && fileOutPutDirectory!=null)
			    return (File)getFileParameter(strName, TMP_FILENAME);
			else 
				return null;
		}
    }

    /**
		@deprecated Replaced by getBaseFilename(String)  
    */
    public String getFileSystemName(String strName)
    {
        return getBaseFilename(strName);
    }

	/**
		Get the uploaded file basename.  This is the basename of the file which
        was provided by the browser itself when the file was chosen using the
        'Browse...' button of the &lt;input type=file ...&gt; input field.

        @return null if strName not found.
        
        @see #getFileParameter(java.lang.String, int)
    */
	public String getBaseFilename(String strName)
    {
        return (String)getFileParameter(strName, FILENAME);
    }
    
	/**
	    Get the uploaded file basename.  This is the file which
        was provided by the browser itself when the file was chosen using the
		'Browse...' button of the &lt;input type=file ...&gt; input field.
	  
        @return null if strName not found.
        
        @see #getFileParameter(java.lang.String, int)
    */
    public String getRawFilename(String strName)
    {
        return (String)getFileParameter(strName, RAW_FILENAME);
    }

    /**
        Returns the File Size of a uploaded file.

        @return -1 if file size not defined.

        @see #getFileParameter(java.lang.String, int)
    */
    public long getFileSize(String strName)
    {
		if(this.maxBytesExceededMode == MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED && 
				this.intContentLength >= this.intMaxContentLength) // file not available.
		{
			return (long)-1;
		}
		else
		{
			Object obj = getFileParameter(strName, SIZE);
			if (obj!=null)
			    return ((Long)obj).longValue();
			else
			    return (long)-1;
		}
    }

    /**
        Access an attribute of a file upload parameter record.

        @param strName is the form field name, used to upload the file.  This identifies
                the formfield location in the storage facility.

        @param strFilename  This is the FileSystemName of the file
        @param type What attribute you want from the File Parameter.
            <pre>
			The following types are supported:
                MultipartRequest.FILENAME, 
                MultipartRequest.CONTENT_TYPE, 
                MultipartRequest.SIZE,
                MultipartRequest.CONTENTS,
                MultipartRequest.TMP_FILENAME
                MultipartRequest.RAW_FILENAME
		    </pre>
			
        <p>The getBaseFilename(),getFile(),getFileSize(),getContentType(),getContents() methods
        all use this method passing in a different type argument.</p>

        <p><b>Note: </b>This class has been changed to provide for future functionality where you
        will be able to access all files uploaded, even if they are uploaded using the same
        form field name.  At this point however, only the first file uploaded via a form
        field name is accessible.</p>

        @see #getContentType(java.lang.String)
        @see #getFileSize(java.lang.String)
        @see #getFileContents(java.lang.String)
        @see #getFile(java.lang.String)
        @see #getBaseFilename(java.lang.String)
    */
    public Object getFileParameter(String strName, int type)
    {
        Object[] objArray = null;
        Object value = htFiles.get(strName);
        if (value instanceof Vector)
            objArray = (Object[]) ((Vector)value).firstElement();
        else
            objArray = (Object[]) htFiles.get(strName);

        // Now ensure valid value.
        if (objArray!=null && type>=FILENAME && type<=RAW_FILENAME)
            return objArray[type];
        else
            return null;
    }

    /**
        This is the main parse method.
    */
    private void parse(InputStream in) throws IOException
    {
        String strContentType = null;
        String strName = null;
        String strFilename = null;
        String strRawFilename = null;
        String strLine = null;
        int read = -1;

        // First run through, check that the first line is a boundary, otherwise throw a exception as format incorrect.
        read = readLine(in, blockOfBytes);
        strLine = read>0? new String(blockOfBytes, 0, read, charEncoding): null;

        // Must be boundary at top of loop, otherwise we have finished.
        if (strLine==null || strLine.indexOf(this.strBoundary)==-1)
		{
            throw new IOException("Invalid Form Data, no boundary encountered.");
		}
		
        // At the top of loop, we assume that the Content-Disposition line is next, otherwise we are at the end.
        while (true)
        {
            // Get Content-Disposition line.
            read = readLine(in, blockOfBytes);
            if (read<=0)
                break; // Nothing to do.
            else
            {
                strLine = new String(blockOfBytes, 0, read, charEncoding);
                
                // Mac IE4 adds extra line after last boundary - 1.21
                if(strLine==null || strLine.length() == 0 || strLine.trim().length() == 0)
                    break;

                // TODO: Improve performance by getting both the name and filename from strLine in one go...
                strName = trimQuotes(getValue("name", strLine));
                // If this is not null, it indicates that we are processing a filename.
                // Now if not null, strip it of any directory information.
                strFilename = trimQuotes(getValue("filename", strLine));

                // No filename specified at all - parameter
                if (strFilename==null)
                {
                    // Skip blank line.
                    readLine(in, blockOfBytes);

					String param = readParameter(in);
					addParameter(strName, param);
                }
                else// (strFilename!=null)
                {
                    // Fix: did not check whether filename was empty string indicating a FILE was not passed.
                    if (strFilename.length()==0)
                    {
                        // FIX 1.14: IE problem with empty filename.
                        read = readLine(in, blockOfBytes);
                        strLine = read>0? new String(blockOfBytes, 0, read, charEncoding): null;
                        
                        // FIX 1.14 IE Problem still: Check for content-type and extra line even though no file specified.
                        if (strLine!=null && strLine.toLowerCase().startsWith("content-type:"))
                            readLine(in, blockOfBytes);

                        // Skip blank line.
                        readLine(in, blockOfBytes);

                        // Fix: FILE INPUT TYPE, but no file passed as input...
                        addFileParameter(strName, new Object[] {null, null, null, null, null, null});

                        readLine(in, blockOfBytes); 
                    }
                    else // File uploaded, or at least a filename was specified, it could still be spurious.
                    {
                        // Need to get the content type.
                        read = readLine(in, blockOfBytes);
                        strLine = read>0? new String(blockOfBytes, 0, read, charEncoding): null;
                        
                        strContentType = "application/octet-stream";
                        // Fix 1.11: If not null AND strLine.length() is long enough.
                        // Modified in 1.19, as we should be checking if it is actually a Content-Type.
                        if (strLine!=null && strLine.toLowerCase().startsWith("content-type:"))//Changed 1.19
                        {
                            strContentType = strLine.substring("content-type:".length()).trim();// Changed 1.13

                            // Skip blank line, but only if a Content-Type was specified.
                            readLine(in, blockOfBytes);
                        }

                        long filesize = -1;

                        // Will remain null for read onto file system uploads.
                        byte[] contentsOfFile = null;

                        // Will remain null if files are loaded into memory.
                        File outFile = null;
                        
                        // Get the BASENAME version of strFilename.
                        strRawFilename = strFilename;
                        strFilename = getBasename(strFilename);

						 // Are we loading files into memory instead of the filesystem?
						if (loadIntoMemory)
						{
						    contentsOfFile = readFile(in);
						    if (contentsOfFile!=null)
						        filesize = contentsOfFile.length;
						}
						else// Read the file onto file system.
						{
						    // If nowhere to write file, then we pass a null file to 
						    // readAndWriteFile, in which case the uploaded file contents
						    // will silently be processed and discarded.
						    if(fileOutPutDirectory != null)
						    {
						        outFile = TempFile.createTempFile("multPartReq", null, fileOutPutDirectory);
						    }
						    filesize = readAndWriteFile(in, outFile);
						}
						
                        // Fix 1.18 for multiple FILE parameter values.
                        if (filesize>0)
                            addFileParameter(strName, new Object[] {strFilename, strContentType, new Long(filesize), contentsOfFile, outFile, strRawFilename});
                        else // Zero length file.
							addFileParameter(strName, new Object[] {strFilename, strContentType, new Long(0), null, null, strRawFilename});
					}
				}
            }
        }// while 
    }
    
    /**
        So we can put the logic for supporting multiple parameters with the same
        form field name in the one location.
    */
    private void addParameter(String strName, String value)
    {
        // Fix 1.16: for multiple parameter values.
        Object objParms = htParameters.get(strName);

		// Add an new entry to the param vector.
        if (objParms instanceof Vector)
		{
            ((Vector)objParms).addElement(value);
		}
        else if (objParms instanceof String)// There is only one entry, so we create a vector!
        {
			Vector vecParms = new Vector();
            vecParms.addElement(objParms);
            vecParms.addElement(value);

            htParameters.put(strName, vecParms);
        }
        else  // first entry for strName.
		{
            htParameters.put(strName, value);
		}
    }

    /**
        So we can put the logic for supporting multiple files with the same
        form field name in the one location.

        Assumes that this method will never be called with a null fileObj or strFilename.
    */
    private void addFileParameter(String strName, Object[] fileObj)
    {
        Object objParms = htFiles.get(strName);

        // Add an new entry to the param vector.
        if (objParms instanceof Vector)
            ((Vector)objParms).addElement(fileObj);
        else if (objParms instanceof Object[])// There is only one entry, so we create a vector!
        {
            Vector vecParms = new Vector();
            vecParms.addElement(objParms);
            vecParms.addElement(fileObj);
        
            htFiles.put(strName, vecParms);
        }
        else  // first entry for strName.
            htFiles.put(strName, fileObj);
    }

    /**
        Read parameters, assume already passed Content-Disposition and blank line.

        @return the value read in.
    */
    private String readParameter(InputStream in) throws IOException
    {
        StringBuffer buf = new StringBuffer();
        int read=-1;

        String line = null;
        while(true)
        {
            read = readLine(in, blockOfBytes);
            if (read<0)
			{
                throw new IOException("Stream ended prematurely.");
			}
			
            // Change v1.18: Only instantiate string once for performance reasons.
            line = new String(blockOfBytes, 0, read, charEncoding);
            if (read<blockOfBytes.length && line.indexOf(this.strBoundary)!=-1)
                break; // Boundary found, we need to finish up.
            else 
                buf.append(line);
        }

        if (buf.length()>0)
            buf.setLength(getLengthMinusEnding(buf));
        return buf.toString();
    }

    /**
        Read from in, write to out, minus last two line ending bytes.
    */
    private long readAndWrite(InputStream in, OutputStream out) throws IOException
    {
        long fileSize = 0;
        int read = -1;

        // This variable will be assigned the bytes actually read.
        byte[] secondLineOfBytes = new byte[blockOfBytes.length];
        // So we do not have to keep creating the second array.
        int sizeOfSecondArray = 0;

        while(true)
        {
            read = readLine(in, blockOfBytes);
            if (read<0)
                throw new IOException("Stream ended prematurely.");
			
            // Found boundary.
            if (read<blockOfBytes.length && new String(blockOfBytes, 0, read, charEncoding).indexOf(this.strBoundary)!=-1)
            {
                // Write the line, minus any line ending bytes.
                //The secondLineOfBytes will NEVER BE NON-NULL if out==null, so there is no need to included this in the test
                if(sizeOfSecondArray!=0)
                {
                    // Only used once, so declare here.
                    int actualLength = getLengthMinusEnding(secondLineOfBytes, sizeOfSecondArray);
                    if (actualLength>0 && out!=null)
                    {
                        out.write(secondLineOfBytes, 0, actualLength);
                        // Update file size.
                        fileSize+=actualLength;
                    }
                }
                break;
            }
            else
            {
                // Write out previous line.
                //The sizeOfSecondArray will NEVER BE ZERO if out==null, so there is no need to included this in the test
                if(sizeOfSecondArray!=0)
                {
                    out.write(secondLineOfBytes, 0, sizeOfSecondArray);
                    // Update file size.
                    fileSize+=sizeOfSecondArray;
                }

                // out will always be null, so there is no need to reset sizeOfSecondArray to zero each time.
                if(out!=null)
                {
                    //Copy the read bytes into the array.
                    System.arraycopy(blockOfBytes,0,secondLineOfBytes,0,read);
                    // That is how many bytes to read from the secondLineOfBytes
                    sizeOfSecondArray=read;
                }
            }
        }

        //Return the number of bytes written to outstream.
        return fileSize;
    }

    /**
        Read a Multipart section that is a file type.  Assumes that the Content-Disposition/Content-Type and blank line
        have already been processed.  So we read until we hit a boundary, then close file and return.

        @exception IOException if an error occurs writing the file.

        @return the number of bytes read.
    */
    private long readAndWriteFile(InputStream in, File outFile) throws IOException
    {
        BufferedOutputStream out = null;
        
		try
		{
			// 1.30rc1 - if max content is larger or equal to actual content length, then we
			// can continue, otherwise, all file content should be silently ignored.
			if(this.intContentLength <= this.intMaxContentLength) // file not available.
			{
				// Because the outFile should be a temporary file provided by the TempFile
		        // class, it should already exist and should be writable.
				if(outFile!=null && outFile.exists() && outFile.canWrite())
				{
					out = new BufferedOutputStream(new FileOutputStream(outFile));
			    }
			}
			
	        long count = readAndWrite(in, out);
            // Count would NOT be larger than zero if 'out' was null.
            if (count==0)
	        {
                // Delete file as empty.
				if (outFile != null) 
				    outFile.delete();
	        }

            return count;
		}
		finally
		{
			if(out!=null)
			{
				out.flush();
                out.close();
			}
		}			
    }

    /**
     *  If the fileOutPutDirectory wasn't specified, just read the file to memory.
     *
     *  @param strName - Url parameter this file was loaded under.
     *  @return contents of file, from which you can garner the size as well.
     */
    private byte[] readFile(InputStream in) throws IOException
    {
		ByteArrayOutputStream out = null;
		
		// 1.30rc1 - if max content is larger or equal to actual content length, then we
		// can continue, otherwise, all file content should be silently ignored.
		if(this.intContentLength <= this.intMaxContentLength)
		{
			// In this case, we do not need to worry about a outputdirectory.
			out = new ByteArrayOutputStream();
		}
		
        long count = readAndWrite(in, out);
        // Count would NOT be larger than zero if out was null.
        if (count>0)
        {
            // Return contents of file to parse method for inclusion in htFiles object.
			if(out!=null)
				return out.toByteArray();
			else
				return null;
        }
        else
            return null;
    }

    /**
        Reads at most READ_BLOCK blocks of data, or a single line whichever is smaller.
        Returns -1, if nothing to read, or we have reached the specified content-length.

        Assumes that bytToBeRead.length indicates the block size to read.

        @return -1 if stream has ended, before a newline encountered (should never happen) OR
        we have read past the Content-Length specified.  (Should also not happen).  Otherwise
        return the number of characters read.  You can test whether the number returned is less
        than bytesToBeRead.length, which indicates that we have read the last line of a file or parameter or 
        a border line, or some other formatting stuff.
	*/
    private int readLine(InputStream in, byte[] bytesToBeRead) throws IOException 
    {
        // Ensure that there is still stuff to read...
        if(this.intTotalRead >= this.intContentLength) 
            return -1;
		
        // Get the length of what we are wanting to read.
        int length = bytesToBeRead.length;

        // End of content, but some servers (apparently) may not realise this and end the InputStream, so
        // we cover ourselves this way.
        if (length > (this.intContentLength - this.intTotalRead))
		{
            length = (int) (this.intContentLength - this.intTotalRead);  // So we only read the data that is left.
		}
		
        int result = readLine(in, bytesToBeRead, 0, length);
        // Only if we actually read something, otherwise something weird has happened, such as the end of stream.
        if (result > 0) 
		{
            this.intTotalRead += result;
		}
		
        return result;  
    }

	/**
        Returns the length of the line minus line ending.

        @param endOfArray   This is because in many cases the byteLine will have garbage data at the end, so we
                            act as though the actual end of the array is this parameter.  If you want to process
                            the complete byteLine, specify byteLine.length as the endOfArray parameter.
    */
    private static final int getLengthMinusEnding(byte byteLine[], int endOfArray)
    {
        if (byteLine==null)
            return 0;
        
        if (endOfArray>=2 && byteLine[endOfArray-2] == '\r' && byteLine[endOfArray-1] == '\n')
            return endOfArray-2;
        else if (endOfArray>=1 && byteLine[endOfArray-1] == '\n' || byteLine[endOfArray-1] == '\r')
            return endOfArray-1;
        else
            return endOfArray;
    }

    private static final int getLengthMinusEnding(StringBuffer buf)
    {
        if (buf.length()>=2 && buf.charAt(buf.length()-2) == '\r' && buf.charAt(buf.length()-1) == '\n')
            return buf.length()-2;
        else if (buf.length()>=1 && buf.charAt(buf.length()-1) == '\n' || buf.charAt(buf.length()-1) == '\r')
            return buf.length()-1;
        else
            return buf.length();
    }
	
    /**
        This needs to support the possibility of a / or a \ separator.

        Returns strFilename after removing all characters before the last
        occurence of / or \.
    */
    private static final String getBasename(String strFilename)
    {
        if (strFilename==null)
            return strFilename;

        int intIndex = strFilename.lastIndexOf("/");
        if (intIndex==-1 || strFilename.lastIndexOf("\\")>intIndex)
            intIndex = strFilename.lastIndexOf("\\");

        if (intIndex!=-1)
            return strFilename.substring(intIndex+1);
        else
            return strFilename;
    }

    /**
        trimQuotes trims any quotes from the start and end of a string and returns the trimmed string...
    */
    private static final String trimQuotes (String strItem)
    {
        // Saves having to go any further....
        if (strItem==null || strItem.indexOf("\"")==-1)
            return strItem;
        
        // Get rid of any whitespace..
        strItem = strItem.trim();

        if (strItem.length()>0 && strItem.charAt(0) == '\"')
            strItem = strItem.substring(1);
        
		if (strItem.length()>0 && strItem.charAt(strItem.length()-1) == '\"')
            strItem = strItem.substring(0, strItem.length()-1);

        return strItem;
    }

    /**
        Format of string name=value; name=value;

        If not found, will return null.
    */
    private static final String getValue(String strName, String strToDecode)
    {
        strName = strName + "=";

        int startIndexOf=0;
        while (startIndexOf<strToDecode.length())
        {
            int indexOf = strToDecode.indexOf(strName, startIndexOf);
            // Ensure either first name, or a space or ; precedes it.
            if (indexOf!=-1)
            {
                if (indexOf==0 || Character.isWhitespace(strToDecode.charAt(indexOf-1)) || strToDecode.charAt(indexOf-1)==';')
                {
                    int endIndexOf = strToDecode.indexOf(";", indexOf+strName.length());
                    if (endIndexOf==-1) // May return an empty string...
                        return strToDecode.substring(indexOf+strName.length());
                    else
                        return strToDecode.substring(indexOf+strName.length(), endIndexOf);
                }
                else
                    startIndexOf=indexOf+strName.length();
            }
            else
                return null;
        }
        return null;
    }

    /**
     * <I>Tomcat's ServletInputStream.readLine(byte[],int,int)  Slightly Modified to utilise in.read()</I>
     * <BR>
     * Reads the input stream, one line at a time. Starting at an
     * offset, reads bytes into an array, until it reads a certain number
     * of bytes or reaches a newline character, which it reads into the
     * array as well.
     *
     * <p>This method <u><b>does not</b></u> returns -1 if it reaches the end of the input
     * stream before reading the maximum number of bytes, it returns -1, if no bytes read.
     *
     * @param b         an array of bytes into which data is read
     *
     * @param off       an integer specifying the character at which
     *                  this method begins reading
     *
     * @param len       an integer specifying the maximum number of 
     *                  bytes to read
     *
     * @return          an integer specifying the actual number of bytes 
     *                  read, or -1 if the end of the stream is reached
     *
     * @exception IOException   if an input or output exception has occurred
     *
     
        Note: We have a problem with Tomcat reporting an erroneous number of bytes, so we need to check this.
        This is the method where we get an infinite loop, but only with binary files.
     */
    private int readLine(InputStream in, byte[] b, int off, int len) throws IOException 
    {
        if (len <= 0) 
            return 0;

        int count = 0, c;

        while ((c = in.read()) != -1) 
        {
            b[off++] = (byte)c;
            count++;
            if (c == '\n' || count == len) 
                break;
        }

        return count > 0 ? count : -1;
    }

    /**
        Use when debugging this object.
    */
    protected void debug(String x)
    {
        if (debug!=null)
        {
            debug.println(x);
            debug.flush();
        }
    }

    /** 
        For debugging.

        Be aware that if you have a form with multiple FILE input types of the same name, only the first one
        will actually be returned from the getFileParameter(...) method, which all the file access methods call.

        So if your upload file was actually uploaded against the second file input field, then it will not be accessible,
        via the methods.
     */
    public String getHtmlTable(String sTrans)
    {
        StringBuffer sbReturn = new StringBuffer();
        
        sbReturn.append("<HTML><HEAD><TITLE>PANADERIA</TITLE>\n");
        sbReturn.append("<link href=\"../css/estilo.css\" rel=\"stylesheet\" type=\"text/css\">\n");  
        sbReturn.append("</style></HEAD><BODY background=\"../imagenes/textura_lineas_blancas.gif\">\n");

        if(this.isMaxBytesExceeded())
        {
            sbReturn.append("<p align=\"center\"><font color=red> Excedido el m�ximo permitido: ("+this.intContentLength+" > "+this.intMaxContentLength+") todos los archivos subidos ignorados.</font></p>\n");
        }
        else 
        {
              sbReturn.append("<p align=\"center\"><font class=\"texto\">Firma del documento e incorporaci&oacute;n a la BD realizada con &eacute;xito.</font></p>\n");
              sbReturn.append("<table align=\"center\" border=0 width=\"600\"><tr><td>\n");
              sbReturn.append("<table border=0 width=\"600\" cellspacing=1 cellpadding=0 bgcolor=\"Gray\">\n");
              sbReturn.append("<tr><td><table width=\"600\" bgcolor=\"6FA0DF\" cellspacing=0 cellpadding=0 align=\"center\" border=\"0\">\n");
              sbReturn.append("<tr><td class=\"titulo_tabla\" width=\"200\">Nombre<td class=\"titulo_tabla\" width=\"150\">Content Type</td><td class=\"titulo_tabla\" width=\"100\">Tama&ntilde;o</td><td class=\"titulo_tabla\" width=\"150\">Transacci&oacute;n @firma</td></tr>\n");
          for (Enumeration e = getFileParameterNames() ; e.hasMoreElements() ;) 
          {
              String strName = (String) e.nextElement();
  
              sbReturn.append("<tr bgcolor=\"A0C8F4\">" +
                              "<font class=\"texto\"><td class=\"texto\">" + (getBaseFilename(strName)!=null?getBaseFilename(strName):"") + "</td>\n");
  
              sbReturn.append("<td class=\"texto\">" + (getContentType(strName)!=null?getContentType(strName):"") + "</td>\n" +
                              "<td class=\"texto\">" + (getFileSize(strName)!=-1?getFileSize(strName)+"":"") + "</td>\n" +
                              "<td class=\"texto\">" + sTrans + "</td>\n" +
                              "</font></tr>\n");
          }
        }
        sbReturn.append("</table></td></tr></table></BODY>\n");
        sbReturn.append("<tr><td align=\"center\" colspan=\"3\"><input type=\"button\" value=\"Volver\" class=\"botonazul\" onClick=\"document.location.href='../consulta.do?doc_adicional=s';\"></td></tr>\n");
        sbReturn.append("</tr></table></BODY>\n");
        return sbReturn.toString();
    }
    public String getHtmlTable2(String sTrans)
    {
        StringBuffer sbReturn = new StringBuffer();
        
        sbReturn.append("<HTML><HEAD><TITLE>PANADERIA</TITLE>\n");
        sbReturn.append("<link href=\"../css/estilo.css\" rel=\"stylesheet\" type=\"text/css\">\n");  
        sbReturn.append("</HEAD><BODY background=\"../imagenes/textura_lineas_blancas.gif\">\n");

        if(this.isMaxBytesExceeded())
        {
            sbReturn.append("<p align=\"center\"><font color=red> Excedido el m&aacute;ximo permitido: ("+this.intContentLength+" > "+this.intMaxContentLength+") todos los archivos subidos ignorados.</font></p>\n");
        }
        else if (sTrans.equals("NO"))
        {
          sbReturn.append("<p align=\"center\"><font color=red> Excedido el m&aacute;ximo permitido de 5 Mb de capacidad. No se ha adjuntado el archivo.</font></p>\n");
        }
        else 
        {
              sbReturn.append("<p align=\"center\"><font class=\"texto\">La incorporaci&oacute;n del documento a la BD se ha realizado con &eacute;xito.</font></p>\n");
              sbReturn.append("<table align=\"center\" border=0 width=\"600\"><tr><td>\n");
              sbReturn.append("<table border=0 width=\"600\" cellspacing=1 cellpadding=0 bgcolor=\"Gray\"><tr><td>\n");
              sbReturn.append("<table align=\"center\" width=\"600\" bgcolor=\"6FA0DF\" cellspacing=0 cellpadding=0 align=\"center\" border=\"0\">\n");
              sbReturn.append("<tr><td class=\"titulo_tabla\" width=\"200\">Nombre<td class=\"titulo_tabla\" width=\"150\">Content Type</td><td class=\"titulo_tabla\" width=\"100\">Tama&ntilde;o</td></tr>\n");
          for (Enumeration e = getFileParameterNames() ; e.hasMoreElements() ;) 
          {
              String strName = (String) e.nextElement();
  
              sbReturn.append("<tr bgcolor=\"A0C8F4\">" +
                              "<font class=\"texto\"><td class=\"texto\">" + (getBaseFilename(strName)!=null?getBaseFilename(strName):"") + "</td>\n");
  
              sbReturn.append("<td class=\"texto\">" + (getContentType(strName)!=null?getContentType(strName):"") + "</td>\n" +
                              "<td class=\"texto\">" + (getFileSize(strName)!=-1?getFileSize(strName)+"":"") + "</td>\n" +
                              "</font></tr>\n");
          }
          sbReturn.append("</table></td></tr></table></td></tr></table>\n");
        }
//        sbReturn.append("</table></td></tr></table></BODY>\n");
        sbReturn.append("<table width=\"100%\" align=\"center\"><tr><td align=\"center\" colspan=\"3\"><input type=\"button\" value=\"Volver\" class=\"botonazul\" onClick=\"document.location.href='../consulta.do?doc_adicional=s';\"></td></tr></table>\n");
        sbReturn.append("</tr></table></BODY>\n");
        return sbReturn.toString();
    }
}
