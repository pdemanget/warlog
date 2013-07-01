package fr.warlog.bus;
/**
 * File model to be serialized
 * @author Philippe
 */
public class FileNode {
  private String name;
  private String path;
  private boolean folder;
  private boolean leaf;
  private long length;
  
  public String getName() {
    return name;
  }
  public String getPath() {
    return path;
  }
  public boolean isFolder() {
    return folder;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setPath(String path) {
    this.path = path;
  }
  public void setFolder(boolean folder) {
    this.folder = folder;
    this.leaf=!folder;
  }
  public long getLength() {
    return length;
  }
  public void setLength(long length) {
    this.length = length;
  }
  public boolean isLeaf() {
    return leaf;
  }
  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
  }
  
}
