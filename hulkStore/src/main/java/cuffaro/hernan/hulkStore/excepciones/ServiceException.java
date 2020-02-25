package cuffaro.hernan.hulkStore.excepciones;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Clase usada para manejar las excepciones de la app.
 * 
 * @author hernan.d.cuffaro
 *
 */
public class ServiceException extends Throwable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8213869653843544704L;
	
	private static final Logger logger = LogManager.getLogger(ServiceException.class);
	
	private Exception exception;
	private String message;
	
	public ServiceException() {}
	
	public ServiceException(String message) {
		this.message = message;
		logger.error(message);
	}
	
	public ServiceException(String message, Exception e) {
		this.message = message;
		this.exception = e;
		logger.error(message, e);
	}
	
	public Exception getException() {
		return exception;
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
