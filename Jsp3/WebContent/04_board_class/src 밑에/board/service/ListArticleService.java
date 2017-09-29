package board.service;

import java.util.List;

import board.model.BoardDao;
import board.model.BoardException;
import board.model.BoardRec;

public class ListArticleService {
	private static ListArticleService instance;
	public static ListArticleService getInstance()  throws BoardException{
		if( instance == null )
		{
			instance = new ListArticleService();
		}
		return instance;
	}
	
	public List <BoardRec> getArticleList() throws BoardException
	{
		// 
		 List <BoardRec> mList = BoardDao.getInstance().selectList();			
		return mList;
	}
		
}
