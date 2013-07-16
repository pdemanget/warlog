package fr.warlog.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.warlog.util.JSONUtils;

/**
 * push events. 
 * @author Philippe
 */
@SuppressWarnings("serial")
public class PushServlet extends HttpServlet {
  
  private static final Map<String, Object> events = new HashMap<>();
  private static final long TIME_OUT = 10* 60_000;
  
  public static void pushEvent(String event,Object o){
	  events.put(event,o);
	  events.notifyAll();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    String event = req.getParameter("event");
    String result = null;
    try{
      Object resultObject=events.get(event);
      if (resultObject == null){
    	  synchronized (events) {
    		  wait(TIME_OUT);	
    	  }
      }
      resultObject=events.get(event);
  	  result = JSONUtils.toJsonString(resultObject);
    }catch (Exception e){
    	result=JSONUtils.toJsonError(e);
    }
    resp.getOutputStream().write(result.getBytes("UTF-8"));
  }
  

}
