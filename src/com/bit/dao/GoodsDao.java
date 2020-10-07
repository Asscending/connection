package com.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.bit.db.ConnectionProvider;
import com.bit.vo.GoodsVo;

public class GoodsDao {

	public static int totalRecord;
	public static int pageSIZE=3;
	public static int totalPage;
	public static int pageGroup=5;
	
	public int getTotalRecord() {
		int n=0;
		String sql="select count(*) from goods";
		try {
			Connection conn=ConnectionProvider.getConnection("c##bit1234", "c##bit1234");
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()) {
				n=rs.getInt(1);
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
		
		
		
		return n;
	}
	
	
	
   public int deleteGoods(int no) {
       int re = -1;
       String sql = "delete goods where no=?";
       try {
           Connection conn = ConnectionProvider.getConnection("c##bit1234", "c##bit1234");
           PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, no);
           re = pstmt.executeUpdate();
           ConnectionProvider.close(null, pstmt, conn);
       }catch (Exception e) {
           // TODO: handle exception
           System.out.println(e.getMessage());
       }
       return re;
   }
    
    
   public int updateGoods(GoodsVo g) {
       int re = -1;
       String sql = "update goods set item=?,price=?,qty=?,fname=?,detail=? where no=?";
       try {       
           Connection conn = ConnectionProvider.getConnection("c##bit1234", "c##bit1234");
           PreparedStatement pstmt = conn.prepareStatement(sql);           
           pstmt.setString(1, g.getItem());
           pstmt.setInt(2, g.getPrice());
           pstmt.setInt(3, g.getQty());
           pstmt.setString(4, g.getFname());
           pstmt.setString(5, g.getDetail());
           pstmt.setInt(6, g.getNo());
           re = pstmt.executeUpdate();
           ConnectionProvider.close(null, pstmt, conn);
       }catch (Exception e) {
           // TODO: handle exception
           System.out.println(e.getMessage());
       }
       return re;
   }
    
    
   public GoodsVo getGoods(int no) {
       GoodsVo g = new GoodsVo();
       String sql = "select * from goods where no=?";
       try {
           Connection conn = ConnectionProvider.getConnection("c##bit1234", "c##bit1234");
           PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, no);
           ResultSet rs = pstmt.executeQuery();
           if(rs.next()) {
               g.setNo(no);
               g.setItem(rs.getString(2));
               g.setPrice(rs.getInt(3));
               g.setQty(rs.getInt(4));
               g.setFname(rs.getString(5));
               g.setDetail(rs.getString(6));
           }
           ConnectionProvider.close(rs, pstmt, conn);
                    
       }catch (Exception e) {
           // TODO: handle exception
           System.out.println(e.getMessage());
       }
       return g;
   }
    
    
   public ArrayList<GoodsVo> listAll(int pageNUM){
	   
	   totalRecord=getTotalRecord();
	   totalPage=(int)Math.ceil(totalRecord/(double)pageSIZE);
	   System.out.println("전체 페이지 수"+totalPage);
	   
	   
       ArrayList<GoodsVo> list = new ArrayList<GoodsVo>();
       String sql ="select no,item,price,qty,fname,detail from (" + 
               " select rownum n, no, item,price,qty,fname,detail from (" + 
               " select * from goods order by no))" + 
               " where n between ? and ?";
       int start = (pageNUM-1)*pageSIZE+1;
       int end=start+pageSIZE-1;
       if(end > totalRecord) {
    	   end = totalRecord;
       }
       
       try {
           Connection conn = ConnectionProvider.getConnection("c##bit1234", "c##bit1234");
           PreparedStatement pstmt=conn.prepareStatement(sql);
           pstmt.setInt(1,start);
           pstmt.setInt(2,end);
           
           ResultSet rs = pstmt.executeQuery();
           while(rs.next()) {
               list.add(new GoodsVo(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
           }
            
           ConnectionProvider.close(rs, pstmt, conn);
                    
       }catch (Exception e) {
           // TODO: handle exception
           System.out.println(e.getMessage());
       }
       return list;
   }
    
    
   public int insertGoods(GoodsVo g) {
       int re = -1;
       try {
           String sql = "insert into goods values(seq_goods.nextval,?,?,?,?,?)";
           Connection conn = ConnectionProvider.getConnection("c##bit1234", "c##bit1234");
           PreparedStatement pstmt = conn.prepareStatement(sql);           
           pstmt.setString(1, g.getItem());
           pstmt.setInt(2, g.getPrice());
           pstmt.setInt(3, g.getQty());
           pstmt.setString(4, g.getFname());
           pstmt.setString(5, g.getDetail());
           re = pstmt.executeUpdate();
           ConnectionProvider.close(null, pstmt, conn);
       }catch (Exception e) {
           // TODO: handle exception
           System.out.println(e.getMessage());
       }
       return re;
   }
}