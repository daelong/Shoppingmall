package project2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UserEnrollServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("NAME");
		String id = request.getParameter("ID");
		String password = request.getParameter("PASSWORD");
		boolean idCheck = false;
		int result = 0; 
		
		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2?serverTimezone=UTC", "root", "root");
			if(conn==null)
				throw new Exception("데이터베이스에 연결할 수 없습니다.");
			
			stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("select id from userinfo where id = '" + id + "';");
			if(rs.next()) {
				idCheck = false;
			}
			else if(!rs.next()) { 
				idCheck = true;
			}
			if(idCheck == true) {
				result = 1;
				stmt = conn.createStatement();
				String command = String.format("insert into userinfo (name, id, password) values('%s', '%s', '%s')", name, id, password);
				int rowNum = stmt.executeUpdate(command);
				if(rowNum < 1)
					throw new Exception("�����͸� DB�� �Է��� �� �����ϴ�.");
			}
			else if(idCheck == false){
				result = 0;
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
			stmt.close();
		} catch (Exception ignored) {}
			try {
			conn.close();
			} catch (Exception ignored) {}
		}		
		if(result == 1) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("UserEnrollCheck.jsp?RESULT="+result);
			dispatcher.forward(request, response);
		}
		else if(result == 0){
			response.sendRedirect("UserEnrollCheck.jsp?RESULT="+result);
		}
	}

}
