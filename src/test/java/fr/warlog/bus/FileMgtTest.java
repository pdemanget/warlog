package fr.warlog.bus;

import org.junit.Assert;
import org.junit.Test;

import fr.warlog.util.JSONUtils;

public class FileMgtTest {
  
  @Test
  public void testRoot(){
    String jsonString = JSONUtils.toJsonString( new FileMgt().roots() );
    System.out.println(jsonString);
    Assert.assertNotNull(jsonString);
  }

}
