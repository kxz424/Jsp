package board.service;

public class ServiceException extends Exception{
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String error) {
		super(error);
	}

}
