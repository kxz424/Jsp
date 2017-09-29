package board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.*;

public class ModifyArticleService implements Service{
	
	private String next;
	
	BoardDao dao = new BoardDao();
	
	public ModifyArticleService(String _next) {
		next = _next;
	}
	
//	public int update( BoardRec rec ) throws BoardException{
//		
//		// DB update
//		int result = BoardDao.getInstance().update(rec);
//	
//		return result;
//		
//	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

		BoardRec rec = new BoardRec();
		rec.setArticleId(Integer.parseInt(request.getParameter("articleId")));
		rec.setTitle(request.getParameter("title"));
		rec.setPassword(request.getParameter("password"));
		rec.setContent(request.getParameter("content"));
		// DB update
		try {
			int result = dao.update(rec);
			request.setAttribute("update", result);
		} catch (BoardException e) {
			e.printStackTrace();
		}
		
		return next;
	}
}
