/**
 * 
 */
package info.jsjackson.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author josan 
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)  //if this annotation is not there, the browser displays 500 error
public class NotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}
	

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
