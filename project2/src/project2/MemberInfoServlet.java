package project2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class MemberInfoServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("LOGIN_ID");
		String name = "";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2?serverTimezone=UTC", "root", "root");
			if(conn == null)
				throw new Exception("데이터베이스에 연결할 수 없습니다.");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from userinfo where id = '" + id + "';");
			if(rs.next())
				name = rs.getString("name");
			session.setAttribute("LOGIN_ID", id);
			session.setAttribute("LOGIN_NAME", name);
			//request.setAttribute("NAME", name);
			//request.setAttribute("ID", id);
		}
		catch(Exception e) {
            throw new ServletException(e);
		}
		finally {
			try {
				stmt.close();
			}
			catch(Exception ignored) {
			}
			try {
				conn.close();
			}
			catch(Exception ignored) {
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("MemberInfo.jsp");
        dispatcher.forward(request, response);
	}
	
}
