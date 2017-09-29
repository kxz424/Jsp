package board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.*;

public class DeleteArticleService implements Service{
	
	private String next;
	
	BoardDao dao = new BoardDao();
		
	public DeleteArticleService(String _next) {
		next = _next;
	}
	
//	public int delete( String id, String password ) throws BoardException{
//		
//		// DB delete
//		int article_id = 0;
//		if( id!=null ) article_id = Integer.parseInt( id );
//		int result = BoardDao.getInstance().delete(article_id, password);
//	
//		return result;
//		
//	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		// DB delete
		int article_id = 0;
		if( id!=null ) article_id = Integer.parseInt( id );
		try {
			int result = dao.delete(article_id, password);
			request.setAttribute("delete", result);			
		} catch (BoardException e) {
			e.printStackTrace();
		}
		return next;
	}
}
