package fr.warlog.bus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.warlog.util.Data;
import fr.warlog.util.JSONUtils;

/**
 * File Management
 * 
 * @author Philippe
 */
public class FileMgt {
  private static final Logger log = Logger.getLogger(FileMgt.class);
  
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

  private FileNode toFileNode(File file) {
    FileNode fileNode = new FileNode();
    fileNode.setFolder(file.isDirectory());
    fileNode.setName(file.getName());
    fileNode.setLength(file.length());
    try {
      fileNode.setPath(file.getCanonicalPath().replace('\\', '/'));
    } catch (IOException e) {
    }
    if("".equals(file.getName())){
      fileNode.setName(fileNode.getPath());
    }
    return fileNode;
  }

  public List<FileNode> list(FileNode root) {
    File[] list = new File(root.getPath()).listFiles();
    return toFileNodes(list);
  }
  
  
  /**
   * Lit le fichier à envoyer 
   */
  public  String readFile( String pMsg ) {
      StringBuffer result = new StringBuffer();
      BufferedReader lBis = null;
      try {
          String line;

          lBis = new BufferedReader( new InputStreamReader( new FileInputStream( pMsg ), "UTF-8" ) );
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
          }
      }

      return result.toString();
  }
  
  /**
   * Read & parse, see also
   * http://stackoverflow.com/questions/6623974/parsing-log4j-layouts-from-log-files
   */
  public  Data<List<Line>>  readFileLines( String pMsg , int start, int limit) {
    List<Line>  result = new ArrayList<Line>();
    Data<List<Line>> dataRes = new Data<List<Line>>(result);
      BufferedReader lBis = null;
      try {
          String line;

          lBis = new BufferedReader( new InputStreamReader( new FileInputStream( pMsg ), "UTF-8" ) );
          int i=0;
          boolean doProcess=true;
          int total=0;
          while ( ( line = lBis.readLine() ) != null ) {
              if(start>0){
                start--;
              }else{
                if(doProcess)
                    result.add(new Line(i++, line) );
                limit--;
                if(limit==0) doProcess=false;
              }
              total++;
          }
          dataRes.setTotal(total);
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
          }
      }

      return dataRes;
  }

}
