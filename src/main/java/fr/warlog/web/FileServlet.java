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
@SuppressWarnings("serial")
public class FileServlet extends HttpServlet {
  
  public int toInt(String s, int defaut){
    try {
      if(s != null)
        return Integer.parseInt(s);
    } catch (NumberFormatException e) {
    }
    return defaut;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    String path = req.getParameter("path");
    String raw = req.getParameter("raw");
    String sep = req.getParameter("sep");
    String pattern = req.getParameter("pattern");
    int col = toInt(req.getParameter("col"),1);
    int start = toInt(req.getParameter("start"),0);
    int limit = toInt(req.getParameter("limit"),-1);
    String result = null;
    try{
    if(raw != null){
      result = new FileMgt().readFile(path);
    }else{
      result = JSONUtils.toJsonString(new FileMgt().readFileLines(path,start,limit,sep,col, pattern));
    }
    }catch (Exception e){
    	result=JSONUtils.toJsonError(e);
    }
    resp.getOutputStream().write(result.getBytes("UTF-8"));
  }
  

}
