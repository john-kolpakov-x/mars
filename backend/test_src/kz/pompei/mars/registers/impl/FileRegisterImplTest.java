package kz.pompei.mars.registers.impl;

import kz.pompei.mars.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

public class FileRegisterImplTest extends ParentTestNg {

  @Test
  public void uploadFile() {


    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);


    System.out.println(Arrays.toString(fileContent));

  }
}
