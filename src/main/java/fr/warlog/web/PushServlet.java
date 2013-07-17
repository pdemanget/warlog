package fr.warlog.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.warlog.bus.FileHookMgt;
import fr.warlog.util.JSONUtils;

/**
 * push events. 
 * @author Philippe
 */
@SuppressWarnings("serial")
public class PushServlet extends HttpServlet {
  
  private static final Map<String, Object> events = new Hashtable<>();
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
    	  //start fileHook
    	  //TODO pour bien faire il faudrait un seul FileHook, mais une file par session.
    	  //note this call is blocking
    	  FileHookMgt.trackFile(event, events);
    	  //wait for the result of filehook
    	  synchronized (events) {
    		  //should do a non blocking track
    		  //events.wait(TIME_OUT);	
    	  }
      }
      resultObject=events.get(event);
      events.remove(event);
  	  result = JSONUtils.toJsonString(resultObject);
    }catch (Exception e){
    	result=JSONUtils.toJsonError(e);
    }
    resp.getOutputStream().write(result.getBytes("UTF-8"));
  }
  

}
