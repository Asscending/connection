package com.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.bit.db.ConnectionProvider;
import com.bit.vo.BoardVo;

public class BoardDao {

	public static int totalRecord; //전체 레코드 수를 저장하기 위한 변수
	
	public static int pageSIZE=3;	// 한 화면에 보여줄 레코드수를 제한하기 위한 변수
	
	public static int totalPage;	// 전체 페이지 수를 저장하기 위한 변수

	public static int pageGROUP=5; // 한 화면에 보여줄 페이지의 수를 제한하기 위한 변수
	
	// 전체 레코드 수를 반환하는 메솓르르 정의해보자
	public int getTotalRecord() {
		int n=0;
		try {
			String sql="select count(*) from board";
			Connection conn=ConnectionProvider.getConnection("c##bit1234", "bit1234");
			Statement stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				n =  rs.getInt(1);
			}
			ConnectionProvider.close(rs, stmt, conn);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return n;
	}
	
	
	
	
    public void updateHit(int no) {
        String sql = "update board set hit=hit+1 where no=?";
        try {
            Connection conn = ConnectionProvider.getConnection("c##bit1234","bit1234");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);
            pstmt.executeUpdate();
            ConnectionProvider.close(null, pstmt, conn);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
     
     
     
    //게시물 수정을 위한 메소드
    public int updateBoard(BoardVo b) {
        int re = -1;
        try {
            String sql = "update board set title=?, content=?, fname=? "
                    + "where no=? and pwd=?";
            Connection conn = ConnectionProvider.getConnection("c##bit1234","bit1234");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, b.getTitle());
            pstmt.setString(2, b.getContent());
            pstmt.setString(3, b.getFname());
            pstmt.setInt(4, b.getNo());
            pstmt.setString(5, b.getPwd());
            re = pstmt.executeUpdate();
            ConnectionProvider.close(null, pstmt, conn);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return re;
    }
     
 
    // 게시물번호와 암호를 매개변수로 전달받아 해당게시물을 삭제하는 메소드
    public int deleteBoard(int no, String pwd) {
        int re = -1;
        try {
            String sql = "delete board where no=? and pwd=?";
            Connection conn = ConnectionProvider.getConnection("c##bit1234","bit1234");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);
            pstmt.setString(2, pwd);
            re = pstmt.executeUpdate();
            ConnectionProvider.close(null, pstmt, conn);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return re;
    }
     
     
     
     
    //파일명을 매개변수로 전달받아 이미지 파일인지 판별하는 메소드
    public boolean isImage(String fname) {
        boolean re = false;
         
        if(fname != null) {
            fname  = fname.toLowerCase();
            String []arr = {".jpg",".gif",".png", ".bmp", ".jpeg"};
            for(int i=0; i<arr.length; i++) {
                if(fname.endsWith(arr[i])) {
                    re = true;
                    break;
                }
            }
        }
        return re;
    }
     
     
     
    //게시물 번호를 매개변수로 전달받아 해당게시물의 정보를 
    //Vo에 담아 반환하는 메소드
    public BoardVo getBoard(int no) {
         
        BoardVo b = new BoardVo();
        try {
            String sql = "select * from board where no=?";
            Connection conn = ConnectionProvider.getConnection("c##bit1234","bit1234");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                b.setNo(rs.getInt(1));
                b.setTitle(rs.getString(2));
                b.setWriter(rs.getString(3));
                b.setPwd(rs.getString(4));
                b.setHit(rs.getInt(5));
                b.setRegdate(rs.getDate(6));
                b.setContent(rs.getString(7));
                b.setFname(rs.getString(8));
                b.setIp(rs.getString(9));
            }
             
            ConnectionProvider.close(rs, pstmt, conn);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return b;
    }
     
     
     
    public ArrayList<BoardVo> listAll(int pageNUM){
    	
    	totalRecord =getTotalRecord();
    	totalPage = totalRecord/ pageSIZE;
    	if( totalRecord % pageSIZE !=0) {
    		totalPage++;
    	}
    	System.out.println("전체 페이지 수: "+totalPage);

    	//현재 페이지에 보여줄 시작레코드이 위치를 계산한다.
    	//만약 전체 레코드가 42개 이고 현재페이지가 1페이지라면
    	//시작 레코드의 위치는 1 // 만약 현재 페이지가 2이면 시작레코드의 위치는 11
    	int start= (pageNUM-1) * pageSIZE+1;
    	int end = start+pageSIZE-1;
    	if(end>totalRecord) {
    		end = totalRecord;
    	}

        String sql = "select no,title,writer,pwd,hit,regdate,content,fname,ip from (" + 
                " select rownum n, no,title,writer,pwd,hit,regdate,content,fname,ip from (" + 
                " select * from board order by no desc))" + 
                " where n between ? and ?";
        ArrayList<BoardVo> list = new ArrayList<BoardVo>();
        try {
            Connection conn = ConnectionProvider.getConnection("c##bit1234","bit1234");
            PreparedStatement pstmt =conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
            ResultSet rs = pstmt.executeQuery();
            //글번호, 글제목, 작성자,조회수, 등록일
            while(rs.next()) {
                BoardVo b = new BoardVo();
                b.setNo(rs.getInt(1));
                b.setTitle(rs.getString(2));
                b.setWriter(rs.getString(3));
                b.setHit(rs.getInt(5));
                b.setRegdate(rs.getDate(6));
                b.setIp(rs.getString(9));
                list.add(b);
            }
             
            ConnectionProvider.close(rs, pstmt, conn);
         
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return list;
    }
     
     
    public int insertBoard(BoardVo b) {
        int re = -1;
        try {
            String sql = "insert into board values(seq_board.nextval,?,?,?,0,sysdate,?,?,?)";
            Connection conn = ConnectionProvider.getConnection("c##bit1234","bit1234");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, b.getTitle());
            pstmt.setString(2, b.getWriter());          
            pstmt.setString(3, b.getPwd());
            pstmt.setString(4, b.getContent());
            pstmt.setString(5, b.getFname());
            pstmt.setString(6, b.getIp());
             
            re = pstmt.executeUpdate();
             
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return re;
    }
}