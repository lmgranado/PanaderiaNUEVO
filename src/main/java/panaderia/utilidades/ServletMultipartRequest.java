package panaderia.utilidades;
import java.io.IOException;
import javax.servlet.*;

import panaderia.utilidades.MultipartRequest;

public class ServletMultipartRequest extends MultipartRequest
{

	/**
	 * Standard Constructor
	 *
	 * @param request				The ServletRequest will be used to initialise the MultipartRequest super class.
	 * @param strSaveDirectory		The temporary directory to save the file from where they can then be moved to wherever by the
	 * 								calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
	 *								will be silently ignored.</B>
	 * @param intMaxReadBytes		Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
	 * @param encoding              Sets the encoding to use. If null, ISO-8859-1 will be used.
	 *
	 * @exception IllegalArgumentException 	If the request.getContentType() does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
	 * @exception IOException				If the request.getContentLength() is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
	 *
	 * @see MultipartRequest#MAX_READ_BYTES
	 */
	public ServletMultipartRequest(ServletRequest request,
								    String strSaveDirectory,
									int intMaxReadBytes,
									String encoding) throws IllegalArgumentException, IOException
	{
 	    super(null, //debug
			request.getContentType(),
			request.getContentLength(),
			request.getInputStream(),
			strSaveDirectory,
			intMaxReadBytes,
			MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
			encoding);
	}

	/**
	 * Memory Constructor
	 *
	 * @param request				The ServletRequest will be used to initialise the MultipartRequest super class.
	 * @param intMaxReadBytes		Overrides the MA_BYTES_READ value, to allow arbitrarily long files.
	 * @param encoding              Sets the encoding to use. If null, ISO-8859-1 will be used.
	 *
	 * @exception IllegalArgumentException 	If the request.getContentType() does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
	 * @exception IOException				If the request.getContentLength() is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
	 *
	 * @see MultipartRequest#MAX_READ_BYTES
	 */
	public ServletMultipartRequest(ServletRequest request,
								int intMaxReadBytes,
								String encoding) throws IllegalArgumentException, IOException
	{
 	    super(null, //debug
			request.getContentType(),
			request.getContentLength(),
			request.getInputStream(),
			intMaxReadBytes,
			MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED,
			encoding);
	}

	/**
	 * Standard Constructor
	 *
	 * @param request				The ServletRequest will be used to initialise the MultipartRequest super class.
	 * @param strSaveDirectory		The temporary directory to save the file from where they can then be moved to wherever by the
	 * 								calling process.  <b>If you specify <u>null</u> for this parameter, then any files uploaded
	 *								will be silently ignored.</B>
	 * @param intMaxReadBytes		Overrides the MAX_BYTES_READ value, to allow arbitrarily long files.
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
	 * @exception IllegalArgumentException 	If the request.getContentType() does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
	 * @exception IOException				If the request.getContentLength() is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
	 *
	 * @see MultipartRequest#MAX_READ_BYTES
	 */
	public ServletMultipartRequest(ServletRequest request,
								    String strSaveDirectory,
									int intMaxReadBytes,
									int maxBytesExceededMode,
									String encoding) throws IllegalArgumentException, IOException
	{
 	    super(null, //debug
			request.getContentType(),
			request.getContentLength(),
			request.getInputStream(),
			strSaveDirectory,
			intMaxReadBytes,
			maxBytesExceededMode,
			encoding);
	}

	/**
	 * Memory Constructor
	 *
	 * @param request				The ServletRequest will be used to initialise the MultipartRequest super class.
	 * @param intMaxReadBytes		Overrides the MA_BYTES_READ value, to allow arbitrarily long files.
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
	 * @exception IllegalArgumentException 	If the request.getContentType() does not contain a Content-Type of "multipart/form-data" or the boundary is not found.
	 * @exception IOException				If the request.getContentLength() is higher than MAX_READ_BYTES or strSaveDirectory is invalid or cannot be written to.
	 *
	 * @see MultipartRequest#MAX_READ_BYTES
	 */
	public ServletMultipartRequest(ServletRequest request,
								int intMaxReadBytes,
								int maxBytesExceededMode,
								String encoding) throws IllegalArgumentException, IOException
	{
 	    super(null, //debug
			request.getContentType(),
			request.getContentLength(),
			request.getInputStream(),
			intMaxReadBytes,
			maxBytesExceededMode,
			encoding);
	}
}