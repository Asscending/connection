package com.bit.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.bit.db.ConnectionProvider;
import com.bit.vo.CustomerVo;
import com.bit.vo.CustomerVo;

public class CustomerDao {
	
	public int insertCustomer(CustomerVo c) {
		int re=-1;
				
	
		return re;
	}
	
	
	public ArrayList<CustomerVo> listAll(){
		ArrayList<CustomerVo> list = new ArrayList<CustomerVo>();
		try {
			String sql="select * from customer";
			Connection conn = ConnectionProvider.getConnection("c##madang","madang");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new CustomerVo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	
}
