package guestbook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guestbook.bean.GuestbookDTO;
import guestbook.dao.GuestbookDAO;

@WebServlet("/GuestbookListServlet")
public class GuestbookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	GuestbookDAO guestbookDAO = new GuestbookDAO();
	List<GuestbookDTO> list = guestbookDAO.select(); 
	
	response.setContentType("text/html;charset=UTF-8");
	PrintWriter out = response.getWriter();
	out.println("<html>");
	out.println("<body>");
	
	if(list != null) {
		out.println("<table border='1' style='border-collapse: collapse;' >");
		for(GuestbookDTO guestBookDTO : list) {
			out.println("<tr>");
			out.println("<td width='20' align='center'>"+ guestBookDTO.getSeq() + "</td>");
			out.println("<td width='200' align='center'>"+ guestBookDTO.getName() + "</td>");
			out.println("<td width='200' align='center'>"+ guestBookDTO.getEmail() + "</td>");
			out.println("<td width='200' align='center'>"+ guestBookDTO.getHomepage() + "</td>");
			out.println("<td width='100' align='center'>"+ guestBookDTO.getSubject() + "</td>");
			out.println("<td width='300' align='center'>"+ guestBookDTO.getContent() + "</td>");
			out.println("<td width='100' align='center'>"+ guestBookDTO.getLogtime() + "</td>");
			out.println("</tr>");
		} //for
		out.println("</table>");
		out.println("<img src='/guestbookServlet/image/spongebob.png' alt='스폰지밥' width='50' height='50' "+ 
				" onclick=location.href='/guestbookServlet/guestbook/guestbookWriteForm.html' style='cursor: pointer;'>");
	}//if
	
	out.println("</html>");
	out.println("</body>");
	}

}
