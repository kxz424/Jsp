package board.control;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.service.DeleteArticleService;
import board.service.ListArticleService;
import board.service.ModifyArticleService;
import board.service.ReplyArticleService;
import board.service.Service;
import board.service.ServiceException;
import board.service.ServiceNull;
import board.service.ViewArticleService;
import board.service.WriteArticleService;

public class BoardControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HashMap serviceMap;
	private String jspDir = "/04_board_class/";
	private String error = "error.jsp";

	public BoardControl() {
		super();
		initService();
	}

	private void initService() {
		serviceMap = new HashMap();
		
		serviceMap.put("list-page", new ListArticleService("BoardList.jsp"));			//게시판 목록
		serviceMap.put("input-form", new ServiceNull("BoardInputForm.jsp"));			//글쓰기 화면
		serviceMap.put("input-confirm", new WriteArticleService("BoardSave.jsp"));		//글 저장
		serviceMap.put("read-page", new ViewArticleService("BoardView.jsp"));			//게시글 상세보기
		serviceMap.put("delete-form", new ServiceNull("BoardDeleteForm.jsp"));			//삭제시 비번 확인
		serviceMap.put("delete-confirm", new DeleteArticleService("BoardDelete.jsp"));	//삭제 처리
		serviceMap.put("modify-form", new ViewArticleService("BoardModifyForm.jsp"));	//수정 화면
		serviceMap.put("modify-confirm", new ModifyArticleService("BoardModify.jsp"));	//수정 처리
		serviceMap.put("reply-form", new ServiceNull("BoardReplyForm.jsp"));			//답글 쓰기 화면
		serviceMap.put("reply-confirm", new ReplyArticleService("BoardReply.jsp"));		//답글 저장
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String nextPage = "";
		String serviceKey = request.getParameter("service");
		
		if(serviceKey == null) {
			serviceKey = "main-page";
		}
		
		System.out.println("serviceKey=" + serviceKey);
		Service service = null;
		
		try {
			if(serviceMap.containsKey(serviceKey)) {
				service = (Service)serviceMap.get(serviceKey);
			}else {
				throw new ServiceException("지정할 명령어가 존재하지 않음");
			}
			
			nextPage = service.execute(request, response);
			System.out.println("nextPage:" + nextPage);
		} catch (ServiceException e) {
			request.setAttribute("javax.servlet.jsp.jspException", e);
			nextPage = error;
			System.out.println("오류 : " + e.getMessage());
		}
		
		RequestDispatcher reqDp = getServletContext().getRequestDispatcher(jspDir + nextPage);
		reqDp.forward(request, response);
	}

}
