package fr.warlog.util;

/**
 * Container of EXT result
 * @author Philippe
 */
public class Data<T> {
  private T data;
  private boolean success=true;
  private Integer total;
  private String message;

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

public void setSuccess(boolean success) {
	this.success = success;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
  
}
