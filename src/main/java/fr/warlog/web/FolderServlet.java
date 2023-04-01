package fr.warlog.web;

import java.io.IOException;
import java.util.List;

import fr.warlog.bus.FileMgt;
import fr.warlog.bus.FileNode;
import fr.warlog.util.JSONUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * list nodes of a folder.
 * @author Philippe
 */
@SuppressWarnings("serial")
public class FolderServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    
    List<FileNode> list;
	try {
		String path = req.getParameter("node");
		if(path==null||"root".equals(path)){
		  list=new FileMgt().roots();
		}else{
		  FileNode root = new FileNode();
		  root.setPath(path);
		  list = new FileMgt().list(root);
		}
		 resp.getOutputStream().write(JSONUtils.toJsonStringWithData(list).getBytes("UTF-8"));
	} catch (Exception e) {
		resp.getOutputStream().write(JSONUtils.toJsonError(e).getBytes("UTF-8"));
	}
    
   
  }
  

}
