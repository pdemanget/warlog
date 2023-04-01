package fr.warlog.bus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.warlog.util.JSONUtils;

public class FileMgtTest {
  
  @Test
  public void testRoot(){
    String jsonString = JSONUtils.toJsonString( new FileMgt().roots() );
    System.out.println(jsonString);
    Assertions.assertNotNull(jsonString);
  }

}
