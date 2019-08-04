package kz.pompei.mars.controller;

import kz.pompei.mars.registers.TestRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private TestRegister testRegister;

  @GetMapping("/asd1")
  public String asd1() {
    return testRegister.asd1();
  }

  @GetMapping("/asd2")
  public String asd2() {
    return testRegister.asd2();
  }

}
