package com.bit.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.dao.GoodsDao;
import com.bit.vo.GoodsVo;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class InsertGoodsServlet
 */
@WebServlet("/goods/insertGoods.do")
public class InsertGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertGoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String view = "insertGoods.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
	        String path = request.getRealPath("goods/img");
	        System.out.println(path);
	        MultipartRequest multi = new MultipartRequest(request, path, "utf-8");
	        GoodsVo g = new GoodsVo();
	        g.setItem(multi.getParameter("item"));
	        g.setPrice(Integer.parseInt(multi.getParameter("price")));
	        g.setQty(Integer.parseInt(multi.getParameter("qty")));
	        g.setDetail(multi.getParameter("detail"));
	         
	        g.setFname(multi.getOriginalFileName("fname"));
	         
	        GoodsDao dao = new GoodsDao();
	        int re  = dao.insertGoods(g);
	        if(re >0) {
	            response.sendRedirect("listGoods.do");
	        }else {
	            request.setAttribute("msg", "상품등록에 실패하였습니다.");
	            String view = "/error.jsp";
	            RequestDispatcher dispatcher = request.getRequestDispatcher(view);
	            dispatcher.forward(request, response);
		}
		
		
	}

}
