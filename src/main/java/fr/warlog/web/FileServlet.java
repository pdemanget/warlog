package fr.warlog.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.warlog.bus.FileMgt;
import fr.warlog.util.JSONUtils;

/**
 * read a file, & returns as lines | raw data 
 * @author Philippe
 */
public class FileServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    String path = req.getParameter("path");
    String raw = req.getParameter("raw");
    String result = null;
    if(raw == null){
      result = new FileMgt().readFile(path);
    }else{
      result = JSONUtils.toJsonString(new FileMgt().readFileJson(path));
    }
    
    resp.getOutputStream().write(result.getBytes("UTF-8"));
  }
  

}
