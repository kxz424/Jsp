package board.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDao;
import board.model.BoardException;
import board.model.BoardRec;

public class ViewArticleService implements Service{
	
	private String next;
	
	BoardDao dao = new BoardDao();
	
	public ViewArticleService(String _next) {
		next = _next;
	}
	
//	public BoardRec getArticleById(String id) throws BoardException
//	{
//		int article_id = 0;
//		if( id != null ) article_id = Integer.parseInt(id);
//		BoardDao dao = BoardDao.getInstance();
//		BoardRec rec = dao.selectById(article_id);		
//		dao.increaseReadCount(id);
//		return rec;
//	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		String id = request.getParameter("id");

		try {
		int article_id = 0;
		BoardRec rec;
		if( id != null ) article_id = Integer.parseInt(id);
			rec = dao.selectById(article_id);		
			dao.increaseReadCount(id);
			request.setAttribute("read", rec);
		
		} catch (BoardException e) {
			e.printStackTrace();
		}
		
		return next;
	}
		
}

