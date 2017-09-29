package board.model;


import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BoardDao
{
	
	private String namespace = "mapper.BoardMapper";
	
	SqlSessionFactory getSqlSessionFactory() {
		InputStream in = null;
		try {
			in = Resources.getResourceAsStream("mybatis-config.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SqlSessionFactory sessFac = new SqlSessionFactoryBuilder().build(in);
		
		return sessFac;
	}
	
	//--------------------------------------------
	//#####	 게시글 입력전에 그 글의 그룹번호를 얻어온다
	public int getGroupId() throws BoardException
	{
		SqlSession sess = getSqlSessionFactory().openSession();
		int groupId=1;
		try{
			groupId = sess.selectOne(namespace + ".selectGroup");
			
			return groupId;
		} finally{
			sess.close();
		}		
	}

	//--------------------------------------------
	//#####	 게시판에 글을 입력시 DB에 저장하는 메소드 
	public int[] insert( BoardRec rec ) throws BoardException
	{
		
		/************************************************
		*/
		int[] result = {0, 0};
		SqlSession sess = getSqlSessionFactory().openSession();		//커넥션 연결
		try{
			result[0] = sess.insert(namespace + ".insertWrite", rec);
			result[1] = sess.selectOne(namespace + ".selectId");
			if(result[0] > 0) {
				sess.commit();
			}else {
				sess.rollback();
			}
			return result;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 입력시 오류  : " + ex.toString() );	
		} finally{
			sess.close();
		}
		
	}

	//--------------------------------------------
	//#####	 전체 레코드를 검색하는 함수
	// 리스트에 보여줄거나 필요한 컬럼 : 게시글번호, 그룹번호, 순서번호, 게시글등록일시, 조회수, 작성자이름,  제목
	//							( 내용, 비밀번호  제외 )
	// 순서번호(sequence_no)로 역순정렬
	public List<BoardRec> selectList(int firstRow, int endRow) throws BoardException
	{
		SqlSession sess = getSqlSessionFactory().openSession();
		List<BoardRec> mList = new ArrayList<BoardRec>();
		boolean isEmpty = true;
		
		HashMap map = new HashMap<>();
		map.put("firstRow", firstRow);
		map.put("endRow", endRow);
		try{
			mList = sess.selectList(namespace + ".selectAll", map);
			
			isEmpty = false;

			if( isEmpty ) return Collections.emptyList();
			
			return mList;
		} finally{
			sess.close();
		}		
	}
	
	//--------------------------------------------
	//#####	 게시글번호에 의한 레코드 검색하는 함수
	public BoardRec selectById(int id) throws BoardException
	{
		SqlSession sess = getSqlSessionFactory().openSession();		//커넥션 연결
		BoardRec rec = new BoardRec();
		try{
			HashMap map = new HashMap<>();
			map.put("id", id);			
			rec = sess.selectOne(namespace + ".selectRead", map);
			
			return rec;
		} finally{
			sess.close();
		}		
	}

	//--------------------------------------------
	//#####	 게시글 보여줄 때 조회수 1 증가
	public void increaseReadCount( String article_id ) throws BoardException
	{
		SqlSession sess = getSqlSessionFactory().openSession();		//커넥션 연결
		try{
			HashMap map = new HashMap<>();
			map.put("article_id", article_id);
			int result = sess.update(namespace + ".updateCnt", map);
			if(result > 0) {
				sess.commit();
			}else {
				sess.rollback();
			}
		
		} finally{
			sess.close();
		}
		
	}
	//--------------------------------------------
	//#####	 게시글 수정할 때
	//		( 게시글번호와 패스워드에 의해 수정 )
	public int update( BoardRec rec ) throws BoardException
	{
		int result = 0;
		SqlSession sess = getSqlSessionFactory().openSession();		//커넥션 연결
		try{
			result = sess.update(namespace + ".updateBoard", rec);
			if(result > 0) {
				sess.commit();
			}else {
				sess.rollback();
			}
			return result; // 나중에 수정된 수를 리턴하시오.
		
		}catch( Exception ex ){
			throw new BoardException("게시판 ) 게시글 수정시 오류  : " + ex.toString() );	
		} finally{
			sess.close();
		}
		
	}
	
	
	//--------------------------------------------
	//#####	 게시글 삭제할 때
	//		( 게시글번호와 패스워드에 의해 삭제 )
	public int delete( int article_id, String password ) throws BoardException
	{

		SqlSession sess = getSqlSessionFactory().openSession();		//커넥션 연결
		try{
			HashMap map = new HashMap<>();
			map.put("article_id", article_id);
			map.put("password", password);
			int result = sess.delete(namespace + ".deleteBoard", map);
			if(result > 0) {
				sess.commit();
			}else {
				sess.rollback();
			}
			return result; // 나중에 수정된 수를 리턴하시오.
		
		}catch( Exception ex ){
			throw new BoardException("게시판 ) 게시글 수정시 오류  : " + ex.toString() );	
		} finally{
			sess.close();
		}
		
	}
	
	
	//----------------------------------------------------
	//#####  부모레코드의 자식레코드 중 마지막 레코드의 순서번호를 검색
	//       ( 제일 작은 번호값이 마지막값임)
	public String selectLastSequenceNumber( String maxSeqNum, String minSeqNum ) throws BoardException
	{
		SqlSession sess = getSqlSessionFactory().openSession();		//커넥션 연결
		try{
			HashMap map = new HashMap<>();
			map.put("maxSeqNum", maxSeqNum);
			map.put("minSeqNum", minSeqNum);
			String result = sess.selectOne(namespace + ".replySeqNum", map);
			if( result == null)
			{				
				return null;
			}
			
			return result;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) 부모와 연관된 자식 레코드 중 마지막 순서번호 얻어오기  : " + ex.toString() );	
		} finally{
			sess.close();
		}			
	}
	
	
	public int getTotalCount() throws BoardException{
		
		SqlSession sess = getSqlSessionFactory().openSession();		//커넥션 연결
		int totalCount = 0;
		try {
			totalCount = sess.selectOne(namespace + ".selectCnt");
			
		} finally{
			sess.close();
		}
		return totalCount;
	}
	
	
}