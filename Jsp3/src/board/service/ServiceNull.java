package board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServiceNull implements Service {
	
	private String next;

	public ServiceNull( String _next ){
		next = _next;
	}

	public String execute( HttpServletRequest request, HttpServletResponse response   ) throws ServiceException{
		return next;
	}

}
