package com.bit.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.dao.GoodsDao;

/**
 * Servlet implementation class ListGoodsServlet
 */
@WebServlet("/goods/listGoods.do")
public class ListGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListGoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNUM=1;
		if(request.getParameter("pageNUM") != null) {
			pageNUM = Integer.parseInt(request.getParameter("pageNUM"));
		}
		System.out.println("페이지번호:"+pageNUM);
		
		 String view="listGoods.jsp";
	        GoodsDao dao = new GoodsDao();
	        request.setAttribute("list", dao.listAll(pageNUM));
	        request.setAttribute("totalPage", dao.totalPage);
	         
	        int startPage = ((pageNUM-1)/dao.pageGroup)*dao.pageGroup+1;
	        int endPage = startPage + dao.pageGroup-1;
	        
	        if(endPage > dao.totalPage) {
	            endPage = dao.totalPage;
	        }
	         
	        request.setAttribute("startPage", startPage);
	        request.setAttribute("endPage", endPage);
         
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
