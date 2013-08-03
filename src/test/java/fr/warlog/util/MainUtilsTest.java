package fr.warlog.util;

import org.junit.*;

/**
 * Test of MainUtils Class
 * @author Philippe
 *
 */
public class MainUtilsTest {
  @Test
  public void testDirList(){
//    MainUtils.listFiles("c:/*");
    System.out.println(MainUtils.merge(MainUtils.listFiles("/*"), "\n  - "));
  }

}
