package project2;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class TopBoardInputServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("LOGIN_ID");
		String name = "";
		String content = "";
		String price = "";
		//String PHOTO = request.getParameter("PHOTO");
		System.out.println(name);
		System.out.println(content);
		System.out.println(price);
		
		 /*String realFolder = "";
		 String filename1 = "";
		 int maxSize = 1024*1024*5;
		 String encType = "UTF-8";
		 String savefile = "img";
		 ServletContext scontext = request.getServletContext();
		 realFolder = scontext.getRealPath(savefile);
		*/
		//String frontPath = "file:///";
		String realFolder = "";
		String filename1 = "";
		int maxSize = 1024*1024*5;
		String encType = "UTF-8";
		String savefile = "img";
		//ServletContext scontext = request.getServletContext();
		realFolder = "C:/ShoppingMall/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/project2/img";
		System.out.println(realFolder);
		/*String realFolder = "";
		String savefile = "img";
		int maxSize = 5*1024*1024;
		String encType="utf-8";
		ServletContext context = request.getServletContext();
		*/
		 try{
			  MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

			  Enumeration<?> files = multi.getFileNames();
			     String file1 = (String)files.nextElement();
			     filename1 = multi.getFilesystemName(file1);
			  name = multi.getParameter("NAME");
			  content = multi.getParameter("CONTENT");
			  price = multi.getParameter("PRICE");
			 } catch(Exception e) {
			  e.printStackTrace();
			 }
		String fullpath = realFolder + "/" + filename1;
		System.out.println(fullpath);
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2?serverTimezone=UTC", "root", "root");
			if(conn==null)
				throw new Exception("데이터베이스에 연결할 수 없습니다.");
			stmt = conn.createStatement();
			String command = String.format("insert into iteminfo (id, name, content, price, photo) values('%s', '%s', '%s', '%s', '%s')", id, name, content, price, fullpath);
			int rowNum = stmt.executeUpdate(command);
			if(rowNum < 1)
				throw new Exception("데이터를 저장할 수 없습니다.");
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("TopBoardInputResult.jsp");
        dispatcher.forward(request, response);
	}

}
