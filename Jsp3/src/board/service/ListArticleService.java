package board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDao;
import board.model.BoardException;
import board.model.BoardRec;

public class ListArticleService implements Service{
	
	private int totalRecCount;		// 전체 레코드 수	
	private int pageTotalCount;		// 전체 페이지 수
	private int countPerPage = 3;	// 한페이지당 레코드 수
	
	int groupNum;					//그룹번호
	int gFirstNum;					//그룹별 시작번호
	int gEndNum;					//그룹별 끝 번호
	int prevPage;					//이전페이지
	int nextPage;					//다음페이지
	
	String pageNum;
	private String next;
	
	BoardDao dao = new BoardDao();
	
	public ListArticleService(String _next) {
		next = _next;
	}
	
//	public List <BoardRec> getArticleList(String pageNum) throws BoardException
//	{
//		int pNum = 1;
//		if(pageNum != null) {
//			pNum = Integer.parseInt(pageNum);
//		}
//		
//		int firstRow = pNum * countPerPage - (countPerPage - 1);
//		int endRow = pNum * countPerPage;
//		
//		List <BoardRec> mList = BoardDao.getInstance().selectList(firstRow, endRow);			
//		return mList;
//	}
	
	public HashMap<String, Integer> getGroup(String pageNum) throws BoardException{
		
		int pNum = 1;	//현재 페이지
		if(pageNum != null) {pNum = Integer.parseInt(pageNum);}
		
		HashMap<String, Integer> map = new HashMap<>();
		
		groupNum = pNum / countPerPage + (pNum % countPerPage > 0? 1:0);	//그룹번호 계산
		gEndNum = groupNum * countPerPage;									//현재 그룹에서 마지막 페이지 번호
		gFirstNum = gEndNum - (countPerPage - 1);							//현재 그룹에서 첫번째 페이지 번호
		prevPage = pNum - 1;												//이전페이지 번호
		nextPage = pNum + 1;												//다음페이지 번호
		
		//그룹의 끝번호가 총 페이지 수보다 클경우 끝번호를 총 페이지 수로 지정
		if(gEndNum > pageTotalCount) {
			gEndNum = pageTotalCount;
		}
		//이전 페이지가 1보다 작을 경우 1로 고정
		if(prevPage < 1) {
			prevPage = 1;
		}
		//다음페이지가 총 페이지수보다 클경우 총 페이지 수로 고정
		if(nextPage > pageTotalCount) {
			nextPage = pageTotalCount;
		}
		
		map.put("start", gFirstNum);
		map.put("end", gEndNum);
		map.put("prev", prevPage);
		map.put("next", nextPage);
		map.put("cnt", countPerPage);
		
		return map;
	}
	
	public int getTotalCount() throws BoardException{
		totalRecCount = dao.getTotalCount();		//db의 row 수를 가져옴
		
		pageTotalCount = totalRecCount / countPerPage;					//전체 페이지 수를 나타냄(전체 레코드 / 기준)
		if(totalRecCount % countPerPage > 0) {pageTotalCount++;}			//나머지가 있을 경우 나타내지 못한 페이지가 있는것이므로 한페이지 추가
		
		return pageTotalCount;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

		int pNum = 1;
		pageNum = request.getParameter("pageNum");
		if(pageNum != null) {
			pNum = Integer.parseInt(pageNum);
		}
		
		int firstRow = pNum * countPerPage - (countPerPage - 1);
		int endRow = pNum * countPerPage;
		
		try {
			request.setAttribute("totalPage", getTotalCount());
			request.setAttribute("group", getGroup(pageNum));
			
			List <BoardRec> mList = dao.selectList(firstRow, endRow);
			request.setAttribute("mList", mList);
		} catch (BoardException e) {
			throw new ServiceException("ListArticleService.java < 목록보기시 > " + e.toString() ); 
		}	
		
		return next;
	}
		
}
