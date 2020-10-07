package com.bit.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVo {
	
	private int no;
	private String title;
	private String writer;
	private String pwd;
	private int hit;
	private Date regdate;
	private String content;
	private String Fname;
	private String ip;
		
	
}
