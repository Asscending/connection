package com.bit.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.dao.BookDao;
import com.bit.vo.BookVo;

/**
 * Servlet implementation class UpdateBookServlet
 */
@WebServlet("/book/updateBook.do")
public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bookid = Integer.parseInt(request.getParameter("bookid"));
        BookDao dao = new BookDao();
        BookVo b = dao.getBook(bookid);
        request.setAttribute("b", b);
        RequestDispatcher dispatcher = request.getRequestDispatcher("updateBook.jsp");
        dispatcher.forward(request, response);
    }
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		BookVo b=new BookVo();
		b.setBookid(Integer.parseInt(request.getParameter("bookid")));
		b.setBookname(request.getParameter("bookname"));
		b.setPublisher(request.getParameter("publisher"));
		b.setPrice(Integer.parseInt(request.getParameter("price")));
		
		BookDao dao=new BookDao();
		int re=dao.updateBook(b);
		if(re>0) {
			response.sendRedirect("listBook.do");
		}else {
			request.setAttribute("msg", "도서수정에 실패하였습니다");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
			
		}
	}

}
