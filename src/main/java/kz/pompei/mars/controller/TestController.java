package kz.pompei.mars.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/asd1")
  public String asd1() {
    return "hello from asd1";
  }

  @GetMapping("/asd2")
  public String asd2() {
    return "hello from asd2";
  }

}
