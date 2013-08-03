package fr.warlog.util;

import org.junit.*;

import fr.warlog.bus.FileNode;

/**
 * Test of MainUtils Class
 * @author Philippe
 *
 */
public class JSONUtilsTest {
  @Test
  public void testToString(){

    FileNode node = new FileNode();
    node.setPath("/aa/bb");
    String jsonString = JSONUtils.toJsonString(node);
    Assert.assertNotNull("json result", jsonString);
  }

}
