package board.service;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.*;

public class WriteArticleService implements Service{
	
	private String next;
		
	public WriteArticleService(String _next) {
		next = _next;
	}
	
//	public BoardRec write( BoardRec rec ) throws BoardException{
//		
//		BoardDao dao = BoardDao.getInstance();
//
//		// 그룹번호(group_id) 지정
//		int groupId = dao.getGroupId();
//		rec.setGroupId(groupId);
//		
//		// 순서번호(sequence_no) 지정
//		DecimalFormat dformat = new DecimalFormat("0000000000");
//		rec.setSequenceNo( dformat.format(groupId) + "999999");
//		
//		// DB에 insert
//		dao.insert(rec);
//			
//		return rec;
//		
//	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		

		BoardDao dao = new BoardDao();
		BoardRec rec = new BoardRec();
		
		rec.setWriterName(request.getParameter("writerName"));
		rec.setTitle(request.getParameter("title"));
		rec.setContent(request.getParameter("content"));
		rec.setPassword(request.getParameter("password"));
		
		try {
			// 그룹번호(group_id) 지정
			int groupId = dao.getGroupId();
			rec.setGroupId(groupId);
			
			// 순서번호(sequence_no) 지정
			DecimalFormat dformat = new DecimalFormat("0000000000");
			rec.setSequenceNo( dformat.format(groupId) + "999999");
			
			// DB에 insert
			dao.insert(rec);
		
		} catch (BoardException e) {
			e.printStackTrace();
		}
		
		return next;
	}
}
