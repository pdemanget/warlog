package fr.warlog.util;

/**
 * Container of EXT result
 * @author Philippe
 */
public class Data<T> {
  private T data;
  private boolean success=true;
  private Integer total;

  public Data(T o) {
    data=o;
  }

  public Object getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public boolean isSuccess() {
    return success;
  }
  
}
