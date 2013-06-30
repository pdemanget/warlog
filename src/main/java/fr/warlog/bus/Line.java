package fr.warlog.bus;
/**
 * line of log
 * @author Philippe
 */
public class Line {
  private int id;
  private String col1;
  private String col2;
  private String col3;
  
  public Line(){}
  public Line(int id, String line) {
    this.id=id;
    String cols[]=line.split(" ",3);
    col1=cols[0];
    if (cols.length>=1)
      col2=cols[1];
    if (cols.length>=2)
      col3=cols[2];
    
  }
  public int getId() {
    return id;
  }
  public String getCol1() {
    return col1;
  }
  public String getCol2() {
    return col2;
  }
  public String getCol3() {
    return col3;
  }
  public void setId(int id) {
    this.id = id;
  }
  public void setCol1(String col1) {
    this.col1 = col1;
  }
  public void setCol2(String col2) {
    this.col2 = col2;
  }
  public void setCol3(String col3) {
    this.col3 = col3;
  }
  
  

}
