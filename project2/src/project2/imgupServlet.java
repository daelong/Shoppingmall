package project2;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class imgupServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		 String realFolder = "";
		 String filename1 = "";
		 int maxSize = 1024*1024*5;
		 String encType = "UTF-8";
		 String savefile = "img";
		 ServletContext scontext = getServletContext();
		 realFolder = scontext.getRealPath(savefile);

		 try{
			  MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

			  Enumeration<?> files = multi.getFileNames();
			  String file1 = (String)files.nextElement();
			  filename1 = multi.getFilesystemName(file1);
			 } catch(Exception e) {
			  e.printStackTrace();
			 }
			 
			 String fullpath = realFolder + "\\" + filename1;
			 request.setAttribute("FULLPATH", fullpath);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("imgup.jsp");
			 dispatcher.forward(request, response);
	}

}
