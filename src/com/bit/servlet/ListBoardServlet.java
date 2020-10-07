package com.bit.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.dao.BoardDao;

/**
 * Servlet implementation class ListBoardServlet
 */
@WebServlet("/board/listBoard.do")
public class ListBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pageNUM=1;
		if(request.getParameter("pageNUM") != null) {
			pageNUM=Integer.parseInt(request.getParameter("pageNUM"));
		}
		System.out.println("페이지 번호: "+pageNUM);
		
		String view = "listBoard.jsp";
        BoardDao dao = new BoardDao();
        request.setAttribute("list", dao.listAll(pageNUM));
        request.setAttribute("totalPage", dao.totalPage); // 전체 페이지 수도 상태유지 한다.
        //만약 현재페이지가 1,2,3,4,5라면 startPage=1
        //만약 현재 페이지가 6,7,8,9,10 이라면 startPage=6
        //현재 페이지번호는 pageNUM
        //한 화면에 보여줄 페이지수는 pageGROUP에 5로 설정되어 있다.
        int startPage=(pageNUM-1)/dao.pageGROUP * dao.pageGROUP +1;
        int endPage=startPage+dao.pageGROUP-1;
        if(endPage > dao.totalPage) {
        	endPage=dao.totalPage;
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
