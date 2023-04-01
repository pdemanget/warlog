package fr.warlog.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    Assertions.assertNotNull("json result", jsonString);
  }

}
