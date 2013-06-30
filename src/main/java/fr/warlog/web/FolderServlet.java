package fr.warlog.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.warlog.bus.FileMgt;
import fr.warlog.bus.FileNode;
import fr.warlog.util.JSONUtils;
/**
 * list nodes of a folder.
 * @author Philippe
 */
public class FolderServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    
    String path = req.getParameter("path");
    List<FileNode> list;
    if(path==null){
      list=new FileMgt().roots();
    }else{
      FileNode root = new FileNode();
      root.setPath(path);
      list = new FileMgt().list(root);
    }
    
    resp.getOutputStream().write(JSONUtils.toJsonString(list).getBytes("UTF-8"));
  }
  

}
