package fr.warlog.util;

/**
 * Container of EXT result
 * @author Philippe
 */
public class Data {
  Object data;
  boolean success=true;

  public Data(Object o) {
    data=o;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
  
}
