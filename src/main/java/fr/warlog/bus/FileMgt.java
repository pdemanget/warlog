package fr.warlog.bus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import fr.warlog.util.Data;
import fr.warlog.util.MainUtils;
import fr.warlog.util.StandardException;
import fr.warlog.util.Utils;

/**
 * File Management
 * 
 * @author Philippe
 */
public class FileMgt {
  private static final Logger log = Logger.getLogger(FileMgt.class);
  private static Map<String,Long> mapId=new Hashtable<>();
  private static long lastId=1;
  
  public List<FileNode> roots() {
    File[] listRoots = File.listRoots();
    List<FileNode> res = toFileNodes(listRoots);
    return res;
  }

  private List<FileNode> toFileNodes(File[] listRoots) {
    List<FileNode> res = new ArrayList<FileNode>();
    for(File file:listRoots){
      res.add(toFileNode(file));
    }
    return res;
  }

  private long nextId(String path){
	  if(mapId.get(path) == null)  mapId.put(path,++lastId);
	  return mapId.get(path);
  }
  private String fromId(long id){
	  for(String path:mapId.keySet()){
		  if(mapId.get(path)==id) return path;
	  }
	  return null;
  }
  
  private FileNode toFileNode(File file) {
    FileNode fileNode = new FileNode();
    fileNode.setFolder(file.isDirectory());
    fileNode.setName(file.getName());
    fileNode.setLength(file.length());
    try {
    	if(MainUtils.isWindows()){
    		fileNode.setPath(file.getCanonicalPath().replace('\\', '/'));
    	}else{
    		fileNode.setPath(file.getCanonicalPath());
    	}
    	fileNode.setId(nextId(file.getPath()));
    } catch (IOException e) {
    }
    if("".equals(file.getName())){
      fileNode.setName(fileNode.getPath());
    }
    return fileNode;
  }

  /**
   * ls.
   * @param root
   * @return
   */
  public List<FileNode> list(FileNode root) {
	if (true){
		root.setId(Long.parseLong(root.getPath()));
		root.setPath(fromId(root.getId()));
	}
    File[] list = Utils.trapNull(new File(root.getPath()).listFiles());
    List<FileNode> fileNodes = toFileNodes(list);
    Collections.sort(fileNodes);
	return fileNodes;
  }
  
  
  
  
  /**
   * Lit le fichier à envoyer 
   */
  public  String readFile( String pMsg ) {
	 
      StringBuffer result = new StringBuffer();
      BufferedReader lBis = null;
      try {
          String line;
          if(pMsg.endsWith(".gz")){
        	  lBis = new BufferedReader( new InputStreamReader( new ZipInputStream( new FileInputStream( pMsg )), "UTF-8" ) );
    	  }else{
    		  lBis = new BufferedReader( new InputStreamReader( new FileInputStream( pMsg ), "UTF-8" ) );
    	  }
          while ( ( line = lBis.readLine() ) != null ) {
              result.append( line );
              result.append( "\n" );
          }

      }
      catch ( IOException e ) {
          log.error( "Can't read file "+ pMsg, e );
      }
      finally {
          try {
              if ( lBis != null ) {
                  lBis.close();
              }

          }
          catch ( IOException e ) {
              log.error( "Can't close file "+ pMsg, e );
              throw new StandardException(e.getMessage(), e);
          }
      }

      return result.toString();
  }
  
  

/**
   * Read & parse, see also
   * http://stackoverflow.com/questions/6623974/parsing-log4j-layouts-from-log-files
 * @param col 
 * @param sep 
   */
  public  Data<List<Line>>  readFileLines( String pMsg , int start, int limit, String sep, int col, String spattern) {
    List<Line>  result = new ArrayList<Line>();
    Data<List<Line>> dataRes = new Data<List<Line>>(result);
      BufferedReader lBis = null;
      try {
          String line;

          if(pMsg.endsWith(".gz")){
                @SuppressWarnings("resource")
                GZIPInputStream zipIS = new GZIPInputStream( new FileInputStream(pMsg));
//        	  ZipEntry nextEntry = zipIS.getNextEntry();
			lBis = new BufferedReader( new InputStreamReader( zipIS, "UTF-8" ) );
    	  }else{
    		  lBis = new BufferedReader( new InputStreamReader( new FileInputStream( pMsg ), "UTF-8" ) );
    	  }
          boolean doProcess=true;
          int total=0;
          Pattern pattern = (spattern == null)?null:Pattern.compile(spattern);
          while ( ( line = lBis.readLine() ) != null ) {
              if(pattern !=null ){
                  Matcher matcher = pattern.matcher(line);
                  if(! matcher.find()) continue;
              }
              if(start>0){
                start--;
              }else{
                if(doProcess)
                    result.add(new Line(total, line,sep,col) );
                limit--;
                if(limit==0) doProcess=false;
              }
              total++;
          }
          dataRes.setTotal(total);
      }
      catch ( IOException e ) {
          log.error( "Can't read file "+ pMsg, e );
          throw new StandardException(e.getMessage(),e); 
      }
      finally {
          try {
              if ( lBis != null ) {
                  lBis.close();
              }

          }
          catch ( IOException e ) {
              log.error( "Can't close file "+ pMsg, e );
          }
      }

      return dataRes;
  }
  
 

}
