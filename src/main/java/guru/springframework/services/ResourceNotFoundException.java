package guru.springframework.services;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		
		super();
	}
	
	/**
	 * 
	 * @param remove the @Lombok.Generated annotation if you made any modification inside the method
	 * the reason I'm using this is for exclude from jacoco code coverage
	 */
	
	@lombok.Generated
	public ResourceNotFoundException(String message) {
		
		super(message);
	}
	
	@lombok.Generated
	public ResourceNotFoundException(String message, Throwable cause) {
		
		super(message, cause);
	}
	
	@lombok.Generated
	public ResourceNotFoundException(Throwable cause) {
		
		super(cause);
	}
	
	@lombok.Generated
	public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
